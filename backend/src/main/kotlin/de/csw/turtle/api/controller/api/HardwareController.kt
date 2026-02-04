package de.csw.turtle.api.controller.api

import de.csw.turtle.api.Permission
import de.csw.turtle.api.dto.hardware.OpenDoorEmojisRequest
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.service.NetworkService
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
import java.time.LocalTime

@RestController
@RequestMapping("/api/hardware")
class HardwareController(
    private val doorControlService: DoorControlService,
    private val lockerService: LockerService,
    private val lockerControlService: LockerControlService,
    private val userService: UserService,
    private val networkService: NetworkService,
    private val systemSettingService: SystemSettingService
) {

    @PostMapping("/door/emojis")
    fun door(
        @RequestBody request: OpenDoorEmojisRequest,
        httpRequest: HttpServletRequest
    ): ResponseEntity<String> {
        val now = LocalTime.now()
        val start = systemSettingService.getTyped<LocalTime>("door.schedule.start")
        val end = systemSettingService.getTyped<LocalTime>("door.schedule.end")
        if (now.isBefore(start) || now.isAfter(end))
            throw HttpException.ServiceUnavailable("Outside of schedule. $start to $end.")

        if (!networkService.isLocalNetwork(httpRequest))
            throw HttpException.Unauthorized("External network.")

        if (userService.getByEmojisOrNull(request.emojis) == null)
            throw HttpException.Unauthorized("Incorrect emojis.")

        val duration = systemSettingService.getTyped<Duration>("door.open.duration")
        val response = doorControlService.trigger(duration)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/door/override")
    fun door(
        @AuthenticationPrincipal user: UserEntity?,
        @RequestParam duration: Duration = Duration.ofSeconds(5)
    ): ResponseEntity<String> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_DOOR))
            throw HttpException.Forbidden()

        val response = doorControlService.trigger(duration)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/locker/override")
    fun locker(
        @AuthenticationPrincipal user: UserEntity?,
        @RequestParam id: Long
    ): ResponseEntity<String> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_LOCKERS))
            throw HttpException.Forbidden()

        val locker = lockerService.get(id)
        val response = lockerControlService.trigger(locker = locker)
        return ResponseEntity.ok(response)
    }

}