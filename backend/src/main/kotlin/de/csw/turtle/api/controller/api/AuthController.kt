package de.csw.turtle.api.controller.api

import de.csw.turtle.api.dto.LoginUserRequest
import de.csw.turtle.api.dto.RegisterUserRequest
import de.csw.turtle.api.dto.get.GetUserResponse
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.mapper.UserMapper
import de.csw.turtle.api.service.AuthService
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.time.Duration

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService,
    private val userMapper: UserMapper
) {

    private fun setCookie(name: String, value: String, duration: Duration, response: HttpServletResponse) {
        //Enable HTTPS only
        //cookie.secure = true

        val attributes = setOf(
            "$name=$value",
            "Max-Age=${duration.toSeconds()}",
            "Path=/",
            "HttpOnly",
            "SameSite=Strict"
        )

        val header = attributes.joinToString(separator = ";") { it }
        response.addHeader("Set-Cookie", header)
    }

    @PostMapping("/register")
    fun register(
        @RequestBody request: RegisterUserRequest
    ): ResponseEntity<GetUserResponse> {
        val entity = authService.register(request)
        val dto = userMapper.get(entity)
        val location = URI.create("/api/users/${entity.id}")
        return ResponseEntity.created(location).body(dto)
    }

    @PostMapping("/login")
    fun login(
        @RequestBody request: LoginUserRequest,
        response: HttpServletResponse
    ): ResponseEntity<Void> {
        val tokens = authService.login(request)

        setCookie("access_token", tokens.accessToken, Duration.ofMinutes(15), response)
        setCookie("refresh_token", tokens.refreshToken, Duration.ofDays(30), response)

        return ResponseEntity.noContent().build()
    }

    @PostMapping("/refresh")
    fun refresh(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): ResponseEntity<Void> {
        val token = request.cookies?.find { it.name == "refresh_token" }?.value
            ?: throw HttpException.Unauthorized("Refresh token is required.")

        val tokens = authService.refresh(token)

        setCookie("access_token", tokens.accessToken, Duration.ofMinutes(15), response)
        setCookie("refresh_token", tokens.refreshToken, Duration.ofDays(30), response)

        return ResponseEntity.noContent().build()
    }

    @PostMapping("/logout")
    fun logout(
        response: HttpServletResponse
    ): ResponseEntity<Void> {
        setCookie("access_token", "", Duration.ZERO, response)
        setCookie("refresh_token", "", Duration.ZERO, response)

        return ResponseEntity.noContent().build()
    }

}