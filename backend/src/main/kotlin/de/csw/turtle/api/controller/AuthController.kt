package de.csw.turtle.api.controller

import de.csw.turtle.api.dto.auth.LoginUserRequest
import de.csw.turtle.api.dto.auth.RegisterUserRequest
import de.csw.turtle.api.dto.get.GetUserResponse
import de.csw.turtle.api.entity.ConfigurationEntity.Key
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.entity.UserEntity.Status
import de.csw.turtle.api.entity.VerificationSessionEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.service.*
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService,
    private val configurationService: ConfigurationService,
    private val userService: UserService,
    private val altchaService: AltchaService,
    private val networkService: NetworkService,
    private val verificationSessionService: VerificationSessionService
) {

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

    data class AuthenticationResponse(
        val user: GetUserResponse,
        val session: UUID? = null
    ) {
        constructor(authentication: AuthService.Authentication) : this(
            user = GetUserResponse(authentication.user),
            session = authentication.session
        )
    }

    @PostMapping("/login")
    fun login(
        @RequestBody request: LoginUserRequest,
        httpRequest: HttpServletRequest,
        response: HttpServletResponse
    ): ResponseEntity<AuthenticationResponse> {
        val ipAddress = networkService.getClientIp(httpRequest)
        if (!altchaService.isTrusted(ipAddress))
            if (request.altchaToken == null || !altchaService.isValid(request.altchaToken))
                throw HttpException.Forbidden("Invalid captcha token.")

        val authentication = authService.login(request, response)

        val dto = AuthenticationResponse(authentication)
        return ResponseEntity.ok(dto)
    }

    @PostMapping("/register")
    fun register(
        @RequestBody request: RegisterUserRequest,
        httpRequest: HttpServletRequest
    ): ResponseEntity<AuthenticationResponse> {
        val ipAddress = networkService.getClientIp(httpRequest)
        if (!altchaService.isTrusted(ipAddress))
            if (request.altchaToken == null || !altchaService.isValid(request.altchaToken))
                throw HttpException.Forbidden("Invalid captcha token.")

        val authentication = authService.register(request)

        val dto = AuthenticationResponse(authentication)
        return ResponseEntity.ok(dto)
    }

    //TODO improve as this is now duplicated with authService
    private val COOKIE_NAME_REFRESH_TOKEN = "refresh_token"

    @PostMapping("/refresh")
    fun refresh(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): ResponseEntity<AuthenticationResponse> {
        val token = request.cookies?.find { it.name == COOKIE_NAME_REFRESH_TOKEN }?.value
            ?: throw HttpException.Unauthorized("Refresh token is required.")

        val authentication = authService.refresh(token, response)

        val dto = AuthenticationResponse(authentication)
        return ResponseEntity.ok(dto)
    }

    @PostMapping("/logout")
    fun logout(
        response: HttpServletResponse
    ): ResponseEntity<Void> {
        authService.logout(response)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/resend-account-verification")
    fun resendAccountVerification(
        @RequestParam uuid: UUID
    ): ResponseEntity<Void> {
        verificationSessionService.resend(uuid)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/submit-account-verification")
    fun accountVerification(
        uuid: UUID,
        @RequestParam code: String
    ): ResponseEntity<Void> {
        authService.verify(uuid, code)
        return ResponseEntity.ok().build()
    }

}