package de.csw.turtle.api.controller.api

import de.csw.turtle.api.dto.LoginUserRequest
import de.csw.turtle.api.dto.RegisterUserRequest
import de.csw.turtle.api.dto.get.GetUserResponse
import de.csw.turtle.api.dto.patch.PatchUserRequest
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.mapper.UserMapper
import de.csw.turtle.api.service.AuthService
import de.csw.turtle.api.service.SystemSettingService
import de.csw.turtle.api.service.UserService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.time.Duration
import java.time.Instant

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService,
    private val systemSettingService: SystemSettingService,
    private val userMapper: UserMapper,
    private val userService: UserService
) {

    private fun setCookie(name: String, value: String, duration: Duration, response: HttpServletResponse) {
        //Enable HTTPS only
        //cookie.secure = true

        val header = buildString {
            append("$name=$value")
            append("; Max-Age=${duration.toSeconds()}")
            append("; Path=/")
            append("; HttpOnly")
            append("; SameSite=Strict")
        }

        response.addHeader("Set-Cookie", header)
    }

    private fun deleteCookie(name: String, response: HttpServletResponse) {
        val header = buildString {
            append("$name=")
            append("; Max-Age=0")
            append("; Expires=Thu, 01 Jan 1970 00:00:00 GMT")
            append("; Path=/")
            append("; HttpOnly")
            append("; SameSite=Strict")
        }

        response.addHeader("Set-Cookie", header)
    }

    private fun getDurationRefresh() = systemSettingService.getTyped<Duration>("auth.jwt.duration.refresh")
    private fun getDurationAccess() = systemSettingService.getTyped<Duration>("auth.jwt.duration.access")

    @GetMapping("/verify")
    fun verify(
        @RequestParam token: String
    ): ResponseEntity<GetUserResponse> {
        val user = userService.getByVerificationToken(token)

        val duration = systemSettingService.getTyped<Duration>("user.verification.duration")
        val expiryDate = user.createdAt.plusMillis(duration.toMillis())
        if (expiryDate.isBefore(Instant.now()))
            throw HttpException.Gone("Token expired.")

        val entity = userService.patch(user.id, PatchUserRequest(verified = true))
        val dto = userMapper.get(entity)
        return ResponseEntity.ok(dto)
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

        setCookie("access_token", tokens.accessToken, getDurationAccess(), response)

        if (request.rememberMe)
            setCookie("refresh_token", tokens.refreshToken, getDurationRefresh(), response)

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

        setCookie("access_token", tokens.accessToken, getDurationAccess(), response)
        setCookie("refresh_token", tokens.refreshToken, getDurationRefresh(), response)

        return ResponseEntity.noContent().build()
    }

    @PostMapping("/logout")
    fun logout(
        response: HttpServletResponse
    ): ResponseEntity<Void> {
        deleteCookie("access_token", response)
        deleteCookie("refresh_token", response)

        return ResponseEntity.noContent().build()
    }

}