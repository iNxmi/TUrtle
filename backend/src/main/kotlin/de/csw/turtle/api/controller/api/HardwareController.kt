package de.csw.turtle.api.controller.api

import de.csw.turtle.api.Permission
import de.csw.turtle.api.dto.OpenDoorEmojisRequest
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.service.NetworkService
import de.csw.turtle.api.service.UserService
import de.csw.turtle.api.service.door.DoorControlService
import de.csw.turtle.api.service.locker.LockerControlService
import de.csw.turtle.api.service.locker.LockerService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/hardware")
class HardwareController(
    private val doorControlService: DoorControlService,
    private val lockerService: LockerService,
    private val lockerControlService: LockerControlService,
    private val userService: UserService,
    private val networkService: NetworkService
) {

    //TODO add min / max duration for door opening in system configuration entity

    val doorSecondsPleaseMoveToSystemSettings = 3

    @PostMapping("/door/emojis")
    fun door(
        @RequestBody request: OpenDoorEmojisRequest,
        httpRequest: HttpServletRequest
    ): ResponseEntity<String> {
        if (!networkService.isLocalNetwork(httpRequest))
            throw HttpException.Unauthorized("External network.")

        if (userService.getByEmojisOrNull(request.emojis) == null)
            throw HttpException.Unauthorized("Incorrect emojis.")

        val response = doorControlService.trigger(seconds = doorSecondsPleaseMoveToSystemSettings)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/door/override")
    fun door(
        @AuthenticationPrincipal user: UserEntity?,
        @RequestParam seconds: Int = 3
    ): ResponseEntity<String> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_DOOR))
            throw HttpException.Forbidden()

        val response = doorControlService.trigger(seconds = seconds)
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
        val response = lockerControlService.trigger(locker = locker, ignoreLocked = true)
        return ResponseEntity.ok(response)
    }

}