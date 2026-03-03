package de.csw.turtle.api.controller

import de.csw.turtle.api.dto.GetConnectionResponse
import de.csw.turtle.api.service.AltchaService
import de.csw.turtle.api.service.NetworkService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/connection")
class ConnectionController(
    private val altchaService: AltchaService,
    private val networkService: NetworkService
) {

    @GetMapping
    fun getConnection(
        request: HttpServletRequest
    ): GetConnectionResponse {
        val ipAddress = networkService.getClientIp(request)
        val isTrusted = altchaService.isTrusted(ipAddress)
        val isLocal = networkService.isLocalNetwork(request)
        return GetConnectionResponse(
            trusted = isTrusted,
            local = isLocal
        )
    }

}