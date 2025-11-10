package de.csw.turtle.api.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/debug")
class DebugController() {

    @GetMapping
    fun debug(
        httpRequest: HttpServletRequest
    ): ResponseEntity<Map<String, String>> {
        val origin = URI.create(httpRequest.requestURL.toString()).host
//        val origin = httpRequest.getHeader(HttpHeaders.ORIGIN)

        val map = mapOf("origin" to origin)
        return ResponseEntity.ok(map)
    }

}