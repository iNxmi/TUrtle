package de.csw.turtle.api.controller

import de.csw.turtle.api.exception.exceptions.debug.DebugException
import de.csw.turtle.api.exception.exceptions.locker.LockerNotFoundException
import de.csw.turtle.api.service.EmailService
import de.csw.turtle.api.service.LockerService
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
    private val lockerService: LockerService
) {

    @GetMapping("/info")
    @PreAuthorize("hasAuthority('debug:info')")
    fun debug(
        httpRequest: HttpServletRequest
    ): ResponseEntity<Map<String, String>> {
        val origin = URI.create(httpRequest.requestURL.toString()).host

        val map = mapOf("origin" to origin)
        return ResponseEntity.ok(map)
    }

    @GetMapping("/door")
    @PreAuthorize("hasAuthority('debug:door')")
    fun door(): ResponseEntity<String> {
        val response = doorControlService.trigger()
        return ResponseEntity.ok(response)
    }

    @GetMapping("/locker/{id}")
    @PreAuthorize("hasAuthority('debug:locker')")
    fun door(@PathVariable id: Long): ResponseEntity<String> {
        val locker = lockerService.getOrNull(id)
            ?: throw LockerNotFoundException(id)

        val response = lockerControlService.trigger(locker)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/exception")
    @PreAuthorize("hasAuthority('debug:exception')")
    fun exception(@RequestParam message: String?): Nothing {
        throw DebugException(message)
    }

    @GetMapping("/email")
    @PreAuthorize("hasAuthority('debug:email')")
    fun exception(@RequestParam to: String, @RequestParam subject: String, @RequestParam text: String) {
        emailService.sendSimpleEmail(to, subject, text)
    }

}