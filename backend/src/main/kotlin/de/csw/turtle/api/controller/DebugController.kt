package de.csw.turtle.api.controller

import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.DebugException
import de.csw.turtle.api.mapper.UserMapper
import de.csw.turtle.api.service.EmailService
import de.csw.turtle.api.service.door.DoorControlService
import de.csw.turtle.api.service.locker.LockerControlService
import de.csw.turtle.api.service.locker.LockerService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.time.Duration

@RestController
@RequestMapping("/api/debug")
class DebugController(
    private val emailService: EmailService,
    private val doorControlService: DoorControlService,
    private val lockerService: LockerService,
    private val lockerControlService: LockerControlService,
    private val userMapper: UserMapper,
) {

    @GetMapping("/me")
    fun me(
        @AuthenticationPrincipal user: UserEntity?
    ): ResponseEntity<Map<String, Any?>> {
        return ResponseEntity.ok(mapOf("authentication" to if (user != null) userMapper.get(user) else null))
    }

    @GetMapping("/headers")
    fun headers(
        request: HttpServletRequest
    ): ResponseEntity<Any?> {
        val headers = request.headerNames.asSequence().associateWith { request.getHeaders(it).asSequence().toList() }
        return ResponseEntity.ok(headers)
    }

    @GetMapping("/cookies")
    fun cookies(
        request: HttpServletRequest
    ): ResponseEntity<Any?> {
        val cookies = request.cookies
        return ResponseEntity.ok(cookies)
    }

    @GetMapping("/exception")
    fun exception(@RequestParam message: String = ""): Nothing {
        throw DebugException(message)
    }

    @GetMapping("/email")
    fun email(
        @RequestParam to: String,
        @RequestParam subject: String,
        @RequestParam text: String
    ): ResponseEntity<Void> {
        emailService.sendSimpleEmail(to, subject, text)

        return ResponseEntity.noContent().build()
    }

    @GetMapping("/door")
    fun door(
        @RequestParam duration: Duration
    ): String = doorControlService.trigger(duration)

    @GetMapping("/locker")
    fun locker(
        @RequestParam id: Long
    ): String {
        val locker = lockerService.get(id)
        return lockerControlService.trigger(locker)
    }

}