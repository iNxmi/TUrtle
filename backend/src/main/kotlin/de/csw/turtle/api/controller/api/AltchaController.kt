package de.csw.turtle.api.controller.api

import de.csw.turtle.api.dto.altcha.GetAltchaChallengeResponse
import de.csw.turtle.api.service.AltchaService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/altcha")
class AltchaController(
    private val altchaService: AltchaService
) {

    @GetMapping("/challenge")
    fun challenge(): ResponseEntity<GetAltchaChallengeResponse> {
        val challenge = altchaService.create()
        val dto = GetAltchaChallengeResponse(challenge)
        return ResponseEntity.ok(dto)
    }

}