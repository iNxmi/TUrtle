package de.csw.turtle.api.controller

import de.csw.turtle.api.exception.DebugException
import de.csw.turtle.api.service.EmailService
import de.csw.turtle.api.service.NetworkService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

//TODO change from /api/debug to /debug (no proxy external access)

@RestController
@RequestMapping("/api/debug")
class DebugController(
    private val emailService: EmailService,
    private val networkService: NetworkService
) {

    @GetMapping("/network")
    fun network(
        request: HttpServletRequest
    ): ResponseEntity<Map<String, Any>> {
        val dto = mapOf(
            "ipAddress" to networkService.getClientIp(request),
            "isLocalNetwork" to networkService.isLocalNetwork(request)
        )
        return ResponseEntity.ok(dto)
    }

    @GetMapping("/headers")
    fun headers(
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        val headers = request.headerNames.asSequence().associateWith { request.getHeaders(it).asSequence().toList() }
        return ResponseEntity.ok(headers)
    }

    @GetMapping("/cookies")
    fun cookies(
        request: HttpServletRequest
    ): ResponseEntity<Any> {
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

}