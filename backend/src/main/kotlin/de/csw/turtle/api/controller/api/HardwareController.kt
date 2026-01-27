package de.csw.turtle.api.controller.api

import de.csw.turtle.api.Permission
import de.csw.turtle.api.dto.OpenDoorEmojisRequest
import de.csw.turtle.api.exception.UnauthorizedException
import de.csw.turtle.api.service.NetworkService
import de.csw.turtle.api.service.PermissionService
import de.csw.turtle.api.service.UserService
import de.csw.turtle.api.service.door.DoorControlService
import de.csw.turtle.api.service.locker.LockerControlService
import de.csw.turtle.api.service.locker.LockerService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/hardware")
class HardwareController(
    private val permissionService: PermissionService,
    private val doorControlService: DoorControlService,
    private val lockerService: LockerService,
    private val lockerControlService: LockerControlService,
    private val userService: UserService,
    private val networkService: NetworkService
) {

    //TODO add min / max duration for door opening in system configuration entity

    val doorSecondsPleaseMoveToSystemSettings = 3

    @PostMapping("/door/emojis")
    fun door(@RequestBody request: OpenDoorEmojisRequest, httpRequest: HttpServletRequest): ResponseEntity<String> {
        if(!networkService.isLocalNetwork(httpRequest))
            throw UnauthorizedException("External network.")

        if(userService.getByEmojisOrNull(request.emojis) == null)
            throw UnauthorizedException("Incorrect emojis.")

        val response = doorControlService.trigger(seconds = doorSecondsPleaseMoveToSystemSettings)
        return ResponseEntity.ok(response)
    }

//    @GetMapping("/door/override")
//    fun door(@RequestParam seconds: Int = 3): ResponseEntity<String> {
//        permissionService.check(BACKEND__API_HARDWARE__DOOR)
//
//        val response = doorControlService.trigger(seconds = seconds)
//        return ResponseEntity.ok(response)
//    }

    @GetMapping("/door/override")
    fun door(@RequestParam seconds: Int = 3): ResponseEntity<String> {
        permissionService.check(Permission.BACKEND__API_HARDWARE__DOOR)

        val response = doorControlService.trigger(seconds = seconds)
        return ResponseEntity.ok(response)
    }

//    @GetMapping("/locker")
//    fun locker(@RequestParam id: Long, request: HttpServletRequest): ResponseEntity<String> {
//        if (!networkService.isLocalNetwork(request))
//            TODO("implement proper exception")
//
//        permissionService.check(BACKEND__API_HARDWARE__LOCKER)
//
//        val locker = lockerService.get(id)
//        val response = lockerControlService.trigger(locker = locker, ignoreLocked = true)
//        return ResponseEntity.ok(response)
//    }

    @GetMapping("/locker/override")
    fun locker(@RequestParam id: Long): ResponseEntity<String> {
        permissionService.check(Permission.BACKEND__API_HARDWARE__LOCKER)

        val locker = lockerService.get(id)
        val response = lockerControlService.trigger(locker = locker, ignoreLocked = true)
        return ResponseEntity.ok(response)
    }

}