package de.csw.turtle.api.service

import de.csw.turtle.api.dto.LoginUserRequest
import de.csw.turtle.api.dto.RegisterUserRequest
import de.csw.turtle.api.dto.create.CreateUserRequest
import de.csw.turtle.api.entity.EmailTemplateEntity
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.HttpException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val userService: UserService,
    private val authenticationManager: AuthenticationManager,
    private val customUserDetailsService: CustomUserDetailsService,
    private val jwtService: JWTService,
    private val emailService: EmailService,
    private val systemSettingService: SystemSettingService,
    private val mustacheService: MustacheService
) {

    data class TokenPair(
        val accessToken: String,
        val refreshToken: String
    )

    @Transactional
    fun register(
        request: RegisterUserRequest
    ): UserEntity {

        //TODO implement random emojis

        val createUserRequest = CreateUserRequest(
            username = request.username,
            firstName = request.firstName,
            lastName = request.lastName,
            email = request.email,
            emojis = "TODO IMPLEMENT RANDOM EMOJI SOMEHOW",
            password = request.password,
        )

        val entity = userService.create(createUserRequest)

        //TODO replace by email template
        val fqdn = systemSettingService.getTyped<String>("general.fqdn")
        val url = "https://$fqdn/api/auth/verify?token=${entity.verificationToken}"
        val variables = mapOf("url" to url)

        val template = systemSettingService.getTyped<EmailTemplateEntity>("email.template.verify")
        val subject = mustacheService.getInserted(template.subject, variables)
        val content = mustacheService.getInserted(template.content, variables)
        emailService.sendSimpleEmail(entity.email, subject, content)

        return entity
    }

    @Transactional
    fun login(
        request: LoginUserRequest
    ): TokenPair {
        try {
            val credentials = UsernamePasswordAuthenticationToken.unauthenticated(
                request.username,
                request.password
            )

            authenticationManager.authenticate(credentials)
        } catch (_: org.springframework.security.authentication.BadCredentialsException) {
            throw HttpException.Unauthorized("Invalid username or password.")
        }

        val userDetails = customUserDetailsService.loadUserByUsername(request.username)

        val accessToken = jwtService.generateAccessToken(userDetails)
        val refreshToken = jwtService.generateRefreshToken(userDetails)

        return TokenPair(accessToken, refreshToken)
    }

    @Transactional
    fun refresh(
        refreshToken: String
    ): TokenPair {
        val username = jwtService.extract(refreshToken)
        val userDetails = customUserDetailsService.loadUserByUsername(username)

        if (!jwtService.isValid(refreshToken, userDetails))
            throw RuntimeException("Invalid refresh token.")

        // ROTATE: issue new refresh token and access token
        val newAccessToken = jwtService.generateAccessToken(userDetails)
        val newRefreshToken = jwtService.generateRefreshToken(userDetails)

        return TokenPair(newAccessToken, newRefreshToken)
    }

}