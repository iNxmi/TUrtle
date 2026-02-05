package de.csw.turtle.api.controller.api

import de.csw.turtle.api.Permission
import de.csw.turtle.api.dto.hardware.OpenDoorEmojisRequest
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.service.NetworkService
import de.csw.turtle.api.service.RoomBookingService
import de.csw.turtle.api.service.SystemSettingService
import de.csw.turtle.api.service.UserService
import de.csw.turtle.api.service.door.DoorControlService
import de.csw.turtle.api.service.locker.LockerControlService
import de.csw.turtle.api.service.locker.LockerService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.time.Duration
import java.time.Instant
import java.time.LocalTime

@RestController
@RequestMapping("/api/hardware")
class HardwareController(
    private val doorControlService: DoorControlService,
    private val lockerService: LockerService,
    private val lockerControlService: LockerControlService,
    private val userService: UserService,
    private val networkService: NetworkService,
    private val systemSettingService: SystemSettingService,
    private val roomBookingService: RoomBookingService
) {

    @PostMapping("/door/emojis")
    fun door(
        @RequestBody request: OpenDoorEmojisRequest,
        httpRequest: HttpServletRequest
    ): ResponseEntity<String> {
        val user = userService.getByEmojisOrNull(request.emojis)

        if (user == null) {
            if (!networkService.isLocalNetwork(httpRequest))
                throw HttpException.Forbidden("External network.")

            throw HttpException.Unauthorized("Incorrect emojis.")
        }

        if (!user.hasPermission(Permission.MANAGE_DOOR))
            checkDoorPermissions(user, httpRequest)

        val duration = systemSettingService.getTyped<Duration>("door.open.duration")
        val response = doorControlService.trigger(duration)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/door/open")
    fun door(
        @AuthenticationPrincipal user: UserEntity?,
        @RequestParam duration: Duration = Duration.ofSeconds(5),
        request: HttpServletRequest
    ): ResponseEntity<String> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_DOOR))
            checkDoorPermissions(user, request)

        val response = doorControlService.trigger(duration)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/locker/open")
    fun locker(
        @AuthenticationPrincipal user: UserEntity?,
        @RequestParam id: Long,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_LOCKERS))
            checkLockerPermissions(user, request)

        val locker = lockerService.get(id)
        val response = lockerControlService.trigger(locker = locker)
        return ResponseEntity.ok(response)
    }

    private fun checkDoorPermissions(user: UserEntity, request: HttpServletRequest) {
        if (!networkService.isLocalNetwork(request))
            throw HttpException.Forbidden("External network.")

        val start = systemSettingService.getTyped<LocalTime>("door.schedule.start")
        val end = systemSettingService.getTyped<LocalTime>("door.schedule.end")
        if (isNowBetween(start, end))
            throw HttpException.ServiceUnavailable("Outside of schedule. $start to $end.")

        val bookings = roomBookingService.getAllCurrent(Instant.now())
        if (bookings.isNotEmpty()) {
            if (bookings.elementAt(0).whitelist.isNotEmpty() && !bookings.elementAt(0).whitelist.contains(user))
                throw HttpException.Forbidden("User with id '${user.id}' is not in whitelist for current Room Booking.")
        }
    }

    private fun checkLockerPermissions(user: UserEntity, request: HttpServletRequest) {
        if (!networkService.isLocalNetwork(request))
            throw HttpException.Forbidden("External network.")

        val start = systemSettingService.getTyped<LocalTime>("locker.schedule.start")
        val end = systemSettingService.getTyped<LocalTime>("locker.schedule.end")
        if (isNowBetween(start, end))
            throw HttpException.ServiceUnavailable("Outside of schedule. $start to $end.")

        TODO("implement check if user has a running device bookings at current time")
    }

    private fun isNowBetween(start: LocalTime, end: LocalTime, now: LocalTime = LocalTime.now()) =
        now.isBefore(start) || now.isAfter(end)

}