package de.csw.turtle.api.controller.api

import de.csw.turtle.api.Permission.BACKEND__API_HARDWARE__DOOR
import de.csw.turtle.api.Permission.BACKEND__API_HARDWARE__LOCKER
import de.csw.turtle.api.service.NetworkService
import de.csw.turtle.api.service.PermissionService
import de.csw.turtle.api.service.door.DoorControlService
import de.csw.turtle.api.service.locker.LockerControlService
import de.csw.turtle.api.service.locker.LockerService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/hardware")
class HardwareController(
    val permissionService: PermissionService,
    val doorControlService: DoorControlService,
    val lockerService: LockerService,
    val lockerControlService: LockerControlService,
    val networkService: NetworkService
) {

    //TODO add min / max duration for door opening in system configuration entity

    @GetMapping("/door")
    fun door(@RequestParam seconds: Int, request: HttpServletRequest): ResponseEntity<String> {
        if (!networkService.isLocalNetwork(request))
            TODO("implement proper exception")

        permissionService.check(BACKEND__API_HARDWARE__DOOR)

        val response = doorControlService.trigger(seconds)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/locker")
    fun locker(@RequestParam id: Long, request: HttpServletRequest): ResponseEntity<String> {
        if (!networkService.isLocalNetwork(request))
            TODO("implement proper exception")

        permissionService.check(BACKEND__API_HARDWARE__LOCKER)

        val locker = lockerService.get(id)
        val response = lockerControlService.trigger(locker)
        return ResponseEntity.ok(response)
    }

}