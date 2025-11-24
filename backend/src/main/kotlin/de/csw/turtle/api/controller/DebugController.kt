package de.csw.turtle.api.controller

import de.csw.turtle.api.exception.exceptions.debug.DebugException
import de.csw.turtle.api.security.Permission
import de.csw.turtle.api.security.RequiresPermission
import de.csw.turtle.api.service.door.DoorControlService
import de.csw.turtle.api.service.locker.Locker
import de.csw.turtle.api.service.locker.LockerControlService
import jakarta.servlet.http.HttpServletRequest
import org.springdoc.core.service.SecurityService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/debug")
class DebugController(
    private val doorControlService: DoorControlService,
    private val lockerControlService: LockerControlService
) {

    @RequiresPermission(Permission.DEBUG_INFO)
    @GetMapping("/info")
    fun debug(
        httpRequest: HttpServletRequest
    ): ResponseEntity<Map<String, String>> {
        val origin = URI.create(httpRequest.requestURL.toString()).host

        val map = mapOf("origin" to origin)
        return ResponseEntity.ok(map)
    }

    @RequiresPermission(Permission.DEBUG_DOOR)
    @GetMapping("/door")
    fun door(): ResponseEntity<String> {
        val response = doorControlService.trigger()
        return ResponseEntity.ok(response)
    }

    @RequiresPermission(Permission.DEBUG_LOCKER)
    @GetMapping("/locker/{id}")
    fun door(@PathVariable id: Int): ResponseEntity<String> {
        val locker = Locker.entries.find { it.id == id }
            ?: throw IllegalArgumentException("No locker found with id '$id'.")

        val response = lockerControlService.trigger(locker)
        return ResponseEntity.ok(response)
    }

    @RequiresPermission(Permission.DEBUG_EXCEPTION)
    @GetMapping("/exception")
    fun exception(@RequestParam message: String?): Nothing {
        throw DebugException(message)
    }

}