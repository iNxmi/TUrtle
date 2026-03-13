package de.csw.turtle.api.controller

import de.csw.turtle.api.dto.auth.LoginUserRequest
import de.csw.turtle.api.dto.auth.RegisterUserRequest
import de.csw.turtle.api.dto.get.GetUserResponse
import de.csw.turtle.api.entity.ConfigurationEntity.Key
import de.csw.turtle.api.entity.TokenEntity
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.entity.UserEntity.Status
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
    private val configurationService: ConfigurationService,
    private val userService: UserService,
    private val altchaService: AltchaService,
    private val networkService: NetworkService,
    private val tokenService: TokenService
) {

    //todo replace cookie generation by more robust method
    private fun setCookie(name: String, value: String, duration: Duration?, response: HttpServletResponse) {
        //TODO Enable HTTPS only
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

    private fun getDuration(type: Type) = configurationService.getTyped<Duration>(type.key)

    @GetMapping("/me")
    fun me(
        @AuthenticationPrincipal user: UserEntity?
    ): ResponseEntity<GetUserResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (user.status != Status.ACTIVE)
            throw HttpException.Forbidden()

        val dto = GetUserResponse(user)
        return ResponseEntity.ok(dto)
    }

    @PostMapping("/login")
    fun login(
        @RequestBody request: LoginUserRequest,
        httpRequest: HttpServletRequest,
        response: HttpServletResponse
    ): ResponseEntity<GetUserResponse> {
        val ipAddress = networkService.getClientIp(httpRequest)
        if (!altchaService.isTrusted(ipAddress))
            if (request.altchaToken == null || !altchaService.isValid(request.altchaToken))
                throw HttpException.Forbidden("Invalid captcha token.")

        val authentication = authService.login(request)

        setCookie(COOKIE_NAME_ACCESS_TOKEN, authentication.accessToken, getDuration(Type.ACCESS), response)

        val refreshTokenDuration = if (request.rememberMe) {
            getDuration(Type.REFRESH)
        } else null

        setCookie(COOKIE_NAME_REFRESH_TOKEN, authentication.refreshToken, refreshTokenDuration, response)

        val dto = GetUserResponse(authentication.user)
        return ResponseEntity.ok(dto)
    }

    @PostMapping("/register")
    fun register(
        @RequestBody request: RegisterUserRequest,
        httpRequest: HttpServletRequest,
        response: HttpServletResponse
    ): ResponseEntity<GetUserResponse> {
        val ipAddress = networkService.getClientIp(httpRequest)
        if (!altchaService.isTrusted(ipAddress))
            if (request.altchaToken == null || !altchaService.isValid(request.altchaToken))
                throw HttpException.Forbidden("Invalid captcha token.")

        val authentication = authService.register(request)

        setCookie(COOKIE_NAME_ACCESS_TOKEN, authentication.accessToken, getDuration(Type.ACCESS), response)

        val dto = GetUserResponse(authentication.user)
        return ResponseEntity.ok(dto)
    }

    @PostMapping("/refresh")
    fun refresh(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): ResponseEntity<GetUserResponse> {
        val token = request.cookies?.find { it.name == COOKIE_NAME_REFRESH_TOKEN }?.value
            ?: throw HttpException.Unauthorized("Refresh token is required.")

        val authentication = authService.refresh(token)

        setCookie(COOKIE_NAME_ACCESS_TOKEN, authentication.accessToken, getDuration(Type.ACCESS), response)
        setCookie(COOKIE_NAME_REFRESH_TOKEN, authentication.refreshToken, getDuration(Type.REFRESH), response)

        val dto = GetUserResponse(authentication.user)
        return ResponseEntity.ok(dto)
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
        @AuthenticationPrincipal user: UserEntity?
    ): ResponseEntity<Void> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (user.status != Status.PENDING_VERIFICATION)
            throw HttpException.Forbidden()

        authService.requestVerification(user)

        return ResponseEntity.ok().build()
    }

    @GetMapping("/verify")
    fun verify(
        @AuthenticationPrincipal user: UserEntity,
        @RequestParam code: String
    ): ResponseEntity<GetUserResponse> {
        if (user.status != Status.PENDING_VERIFICATION)
            throw HttpException.Forbidden()

        val token = tokenService.getByCode(code)
            ?: throw HttpException.NotFound("No token with code '$code'.")

        if (token.type != TokenEntity.Type.VERIFICATION)
            throw HttpException.BadRequest("Invalid token type.")

        if (token.isExpired())
            throw HttpException.Unauthorized("Token expired.")

        userService.removeToken(user, token)
        tokenService.delete(token.id)

        val regexes = configurationService.getTyped<List<String>>(Key.USER_EMAIL_TRUSTED).map { Regex(it) }
        val isTrustedEmail = regexes.any { it.matches(user.email) }
        val newStatus = if (isTrustedEmail) Status.ACTIVE else Status.PENDING_APPROVAL

        val entity = userService.patch(id = user.id, status = newStatus)
        val dto = GetUserResponse(entity)
        return ResponseEntity.ok(dto)
    }

}