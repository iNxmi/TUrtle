package de.csw.turtle.api.controller

import de.csw.turtle.api.Permission
import de.csw.turtle.api.exception.DebugException
import de.csw.turtle.api.service.EmailService
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
import java.net.URI

@RestController
@RequestMapping("/debug")
class DebugController(
    private val emailService: EmailService,
    private val permissionService: PermissionService,
    private val doorControlService: DoorControlService,
    private val lockerService: LockerService,
    private val lockerControlService: LockerControlService
) {

    @GetMapping("/info")
    fun debug(
        httpRequest: HttpServletRequest
    ): ResponseEntity<Map<String, String>> {
        permissionService.check(Permission.BACKEND__DEBUG__INFO)

        val origin = URI.create(httpRequest.requestURL.toString()).host

        val map = mapOf("origin" to origin)
        return ResponseEntity.ok(map)
    }

    @GetMapping("/exception")
    fun exception(@RequestParam message: String?): Nothing {
        permissionService.check(Permission.BACKEND__DEBUG__EXCEPTION)

        throw DebugException(message)
    }

    @GetMapping("/email")
    fun exception(@RequestParam to: String, @RequestParam subject: String, @RequestParam text: String) {
        permissionService.check(Permission.BACKEND__DEBUG__EMAIL)

        emailService.sendSimpleEmail(to, subject, text)
    }

    @GetMapping("/door")
    fun exception(@RequestParam seconds: Int): String {
        permissionService.check(Permission.BACKEND__DEBUG__DOOR)

        return doorControlService.trigger(seconds)
    }

    @GetMapping("/locker")
    fun exception(@RequestParam id: Long, @RequestParam ignoreLocked: Boolean = false): String {
        permissionService.check(Permission.BACKEND__DEBUG__LOCKER)

        val locker = lockerService.get(id)
        return lockerControlService.trigger(locker, ignoreLocked = ignoreLocked)
    }

}