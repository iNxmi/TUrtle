package de.csw.turtle.api.controller.api

import de.csw.turtle.api.Settings
import de.csw.turtle.api.dto.auth.LoginUserRequest
import de.csw.turtle.api.dto.get.GetUserResponse
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.service.*
import de.csw.turtle.api.service.JWTService.Type
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.time.Duration
import java.time.Instant

private const val COOKIE_NAME_ACCESS_TOKEN = "access_token"
private const val COOKIE_NAME_REFRESH_TOKEN = "refresh_token"

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService,
    private val systemSettingService: SystemSettingService,
    private val userService: UserService,
    private val altchaService: AltchaService,
    private val networkService: NetworkService
) {

    //todo replace cookie generation by more robust method
    private fun setCookie(name: String, value: String, duration: Duration?, response: HttpServletResponse) {
        //Enable HTTPS only
        //cookie.secure = true

        val header = buildString {
            append("$name=$value")

            if (duration != null)
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

    private fun getDuration(type: Type) = systemSettingService.getTyped<Duration>(type.setting)

    @GetMapping("/me")
    fun verify(
        @AuthenticationPrincipal user: UserEntity?
    ): ResponseEntity<GetUserResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        val dto = GetUserResponse(user)
        return ResponseEntity.ok(dto)
    }

    @GetMapping("/verify")
    fun verify(
        @RequestParam token: String
    ): ResponseEntity<GetUserResponse> {
        val user = userService.getByVerificationToken(token)

        val duration = systemSettingService.getTyped<Duration>(Settings.USER_VERIFICATION_DURATION)
        val expiryDate = user.createdAt.plusMillis(duration.toMillis())
        if (expiryDate.isBefore(Instant.now()))
            throw HttpException.Gone("Token expired.")

        val entity = userService.patch(id = user.id, verified = true)
        val dto = GetUserResponse(entity)
        return ResponseEntity.ok(dto)
    }

    @PostMapping("/login")
    fun login(
        @RequestBody request: LoginUserRequest,
        httpRequest: HttpServletRequest,
        response: HttpServletResponse
    ): ResponseEntity<Void> {
        val ipAddress = networkService.getClientIp(httpRequest)
        if (request.altchaToken == null || !altchaService.isValid(ipAddress, request.altchaToken))
            throw HttpException.Forbidden("Invalid captcha token.")

        val tokens = authService.login(request)

        setCookie(COOKIE_NAME_ACCESS_TOKEN, tokens.accessToken, getDuration(Type.ACCESS), response)

        val refreshTokenDuration = if (request.rememberMe) {
            getDuration(Type.REFRESH)
        } else null

        setCookie(COOKIE_NAME_REFRESH_TOKEN, tokens.refreshToken, refreshTokenDuration, response)

        return ResponseEntity.noContent().build()
    }

    @PostMapping("/refresh")
    fun refresh(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): ResponseEntity<Void> {
        val token = request.cookies?.find { it.name == COOKIE_NAME_REFRESH_TOKEN }?.value
            ?: throw HttpException.Unauthorized("Refresh token is required.")

        val tokens = authService.refresh(token)

        setCookie(COOKIE_NAME_ACCESS_TOKEN, tokens.accessToken, getDuration(Type.ACCESS), response)
        setCookie(COOKIE_NAME_REFRESH_TOKEN, tokens.refreshToken, getDuration(Type.REFRESH), response)

        return ResponseEntity.noContent().build()
    }

    @PostMapping("/logout")
    fun logout(
        response: HttpServletResponse
    ): ResponseEntity<Void> {
        deleteCookie(COOKIE_NAME_ACCESS_TOKEN, response)
        deleteCookie(COOKIE_NAME_REFRESH_TOKEN, response)

        return ResponseEntity.noContent().build()
    }

}