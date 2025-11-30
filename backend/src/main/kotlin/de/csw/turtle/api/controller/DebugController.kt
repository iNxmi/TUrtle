package de.csw.turtle.api.controller

import de.csw.turtle.api.Permission.*
import de.csw.turtle.api.exception.exceptions.debug.DebugException
import de.csw.turtle.api.exception.exceptions.locker.LockerNotFoundException
import de.csw.turtle.api.service.EmailService
import de.csw.turtle.api.service.locker.LockerService
import de.csw.turtle.api.service.SecurityService
import de.csw.turtle.api.service.door.DoorControlService
import de.csw.turtle.api.service.locker.LockerControlService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/debug")
class DebugController(
    private val doorControlService: DoorControlService,
    private val lockerControlService: LockerControlService,
    private val emailService: EmailService,
    private val lockerService: LockerService,
    private val securityService: SecurityService
) {

    @GetMapping("/info")
    @PreAuthorize("hasAuthority('DEBUG__INFO')")
    fun debug(
        httpRequest: HttpServletRequest
    ): ResponseEntity<Map<String, String>> {
        securityService.hasPermission(DEBUG__INFO)

        val origin = URI.create(httpRequest.requestURL.toString()).host

        val map = mapOf("origin" to origin)
        return ResponseEntity.ok(map)
    }

    @GetMapping("/door")
    @PreAuthorize("hasAuthority('DEBUG__DOOR')")
    fun door(): ResponseEntity<String> {
        securityService.hasPermission(DEBUG__DOOR)

        val response = doorControlService.trigger()
        return ResponseEntity.ok(response)
    }

    @GetMapping("/locker/{id}")
    @PreAuthorize("hasAuthority('DEBUG__LOCKER')")
    fun door(@PathVariable id: Long): ResponseEntity<String> {
        securityService.hasPermission(DEBUG__LOCKER)

        val locker = lockerService.getOrNull(id)
            ?: throw LockerNotFoundException(id)

        val response = lockerControlService.trigger(locker)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/exception")
    @PreAuthorize("hasAuthority('DEBUG__EXCEPTION')")
    fun exception(@RequestParam message: String?): Nothing {
        securityService.hasPermission(DEBUG__EXCEPTION)

        throw DebugException(message)
    }

    @GetMapping("/email")
    @PreAuthorize("hasAuthority('DEBUG__EMAIL')")
    fun exception(@RequestParam to: String, @RequestParam subject: String, @RequestParam text: String) {
        securityService.hasPermission(DEBUG__EMAIL)

        emailService.sendSimpleEmail(to, subject, text)
    }

}