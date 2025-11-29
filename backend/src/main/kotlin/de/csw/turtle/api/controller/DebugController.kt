package de.csw.turtle.api.controller

import de.csw.turtle.api.Permission.*
import de.csw.turtle.api.exception.exceptions.debug.DebugException
import de.csw.turtle.api.exception.exceptions.locker.LockerNotFoundException
import de.csw.turtle.api.service.EmailService
import de.csw.turtle.api.service.LockerService
import de.csw.turtle.api.service.SecurityService
import de.csw.turtle.api.service.door.DoorControlService
import de.csw.turtle.api.service.locker.LockerControlService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
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
    fun debug(
        httpRequest: HttpServletRequest
    ): ResponseEntity<Map<String, String>> {
        securityService.required(DEBUG__INFO)

        val origin = URI.create(httpRequest.requestURL.toString()).host

        val map = mapOf("origin" to origin)
        return ResponseEntity.ok(map)
    }

    @GetMapping("/door")
    fun door(): ResponseEntity<String> {
        securityService.required(DEBUG__DOOR)

        val response = doorControlService.trigger()
        return ResponseEntity.ok(response)
    }

    @GetMapping("/locker/{id}")
    fun door(@PathVariable id: Long): ResponseEntity<String> {
        securityService.required(DEBUG__LOCKER)

        val locker = lockerService.getOrNull(id)
            ?: throw LockerNotFoundException(id)

        val response = lockerControlService.trigger(locker)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/exception")
    fun exception(@RequestParam message: String?): Nothing {
        securityService.required(DEBUG__EXCEPTION)

        throw DebugException(message)
    }

    @GetMapping("/email")
    fun exception(@RequestParam to: String, @RequestParam subject: String, @RequestParam text: String) {
        securityService.required(DEBUG__EMAIL)

        emailService.sendSimpleEmail(to, subject, text)
    }

}