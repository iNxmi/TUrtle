package de.csw.turtle.api.controller.api

import de.csw.turtle.api.Permission
import de.csw.turtle.api.Settings
import de.csw.turtle.api.dto.hardware.OpenDoorEmojisRequest
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.service.*
import de.csw.turtle.api.service.door.DoorControlService
import de.csw.turtle.api.service.locker.LockerControlService
import de.csw.turtle.api.service.locker.LockerService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.time.Duration
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
    private val itemBookingService: ItemBookingService,
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

        checkDoorPermissions(user, httpRequest)

        val duration = systemSettingService.getTyped<Duration>(Settings.DOOR_OPEN_DURATION)
        val response = doorControlService.trigger(duration)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/door/open")
    fun door(
        @AuthenticationPrincipal user: UserEntity?,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        if (user == null)
            throw HttpException.Unauthorized()

        checkDoorPermissions(user, request)

        val duration = systemSettingService.getTyped<Duration>(Settings.DOOR_OPEN_DURATION)
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

        checkLockerPermissions(user, id, request)

        val locker = lockerService.getById(id)
            ?: throw HttpException.NotFound()

        val response = lockerControlService.trigger(locker = locker)
        return ResponseEntity.ok(response)
    }

    private fun checkDoorPermissions(user: UserEntity, request: HttpServletRequest) {
        if (user.hasPermission(Permission.MANAGE_DOOR))
            return

        if (!networkService.isLocalNetwork(request))
            throw HttpException.Forbidden("External network.")

        val start = systemSettingService.getTyped<LocalTime>(Settings.DOOR_SCHEDULE_START)
        val end = systemSettingService.getTyped<LocalTime>(Settings.DOOR_SCHEDULE_END)
        if (isNowBetween(start, end))
            throw HttpException.ServiceUnavailable("Outside of schedule. $start to $end.")

        val booking = roomBookingService.getCurrent() ?: return
        if (booking.whitelist.isNotEmpty() && !booking.whitelist.contains(user))
            throw HttpException.Unauthorized("User '${user.id}' not in whitelist for current Room Booking.")
    }

    private fun checkLockerPermissions(user: UserEntity,lockerId: Long, request: HttpServletRequest) {
        if (user.hasPermission(Permission.MANAGE_LOCKERS))
            return

        if (!networkService.isLocalNetwork(request))
            throw HttpException.Forbidden("External network.")

        val start = systemSettingService.getTyped<LocalTime>(Settings.LOCKER_SCHEDULE_START)
        val end = systemSettingService.getTyped<LocalTime>(Settings.LOCKER_SCHEDULE_END)
        if (isNowBetween(start, end))
            throw HttpException.ServiceUnavailable("Outside of schedule. $start to $end.")

        if(itemBookingService.getCurrent(user.id, lockerId).isEmpty())
            throw HttpException.Forbidden("No Item Bookings found for this locker and user.")
    }

    private fun isNowBetween(start: LocalTime, end: LocalTime, now: LocalTime = LocalTime.now()) =
        now.isBefore(start) || now.isAfter(end)
}