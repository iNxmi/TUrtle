package de.csw.turtle.api.controller

import de.csw.turtle.api.Settings
import de.csw.turtle.api.dto.auth.LoginUserRequest
import de.csw.turtle.api.dto.auth.ResetUserPasswordRequest
import de.csw.turtle.api.dto.auth.VerificationRequest
import de.csw.turtle.api.dto.get.GetUserResponse
import de.csw.turtle.api.entity.TokenEntity
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

private const val COOKIE_NAME_ACCESS_TOKEN = "access_token"
private const val COOKIE_NAME_REFRESH_TOKEN = "refresh_token"

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService,
    private val systemSettingService: SystemSettingService,
    private val userService: UserService,
    private val altchaService: AltchaService,
    private val networkService: NetworkService,
    private val tokenService: TokenService
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

    @PostMapping("/login")
    fun login(
        @RequestBody request: LoginUserRequest,
        httpRequest: HttpServletRequest,
        response: HttpServletResponse
    ): ResponseEntity<Void> {
        val ipAddress = networkService.getClientIp(httpRequest)
        if (!altchaService.isTrusted(ipAddress))
            if (request.altchaToken == null || !altchaService.isValid(request.altchaToken))
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

    @PostMapping("/request-verification")
    fun requestVerification(
        @RequestBody request: VerificationRequest
    ): ResponseEntity<Void> {
        val user = userService.getByEmailOrNull(request.email)

        if (user != null)
            authService.requestVerification(user)

        return ResponseEntity.ok().build()
    }

    @GetMapping("/verify")
    fun verify(
        @RequestParam uuid: String
    ): ResponseEntity<GetUserResponse> {
        val token = tokenService.getByUuid(uuid)
            ?: throw HttpException.NotFound("No token with uuid '$uuid'.")

        if (token.type != TokenEntity.Type.VERIFICATION)
            throw HttpException.BadRequest("Invalid token type.")

        if (token.isExpired())
            throw HttpException.Unauthorized("Token expired.")

        val user = userService.getByToken(token)
            ?: throw HttpException.NotFound()

        val regexes = systemSettingService.getTyped<List<String>>(Settings.USER_EMAIL_TRUSTED).map { Regex(it) }
        val isTrustedEmail = regexes.any { it.matches(user.email) }
        val newStatus = if (isTrustedEmail) UserEntity.Status.ACTIVE else UserEntity.Status.PENDING_APPROVAL

        val entity = userService.patch(id = user.id, status = newStatus)
        val dto = GetUserResponse(entity)
        return ResponseEntity.ok(dto)
    }

    @PostMapping("/reset-password")
    fun reset(
        @RequestBody request: ResetUserPasswordRequest
    ) {

    }

}