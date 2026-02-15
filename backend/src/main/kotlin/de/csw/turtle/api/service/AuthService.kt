package de.csw.turtle.api.service

import de.csw.turtle.api.dto.auth.LoginUserRequest
import de.csw.turtle.api.entity.EmailTemplateEntity
import de.csw.turtle.api.entity.ConfigurationEntity.Key
import de.csw.turtle.api.entity.TokenEntity.Type
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.HttpException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.thymeleaf.context.Context
import java.time.Duration

@Service
class AuthService(
    private val userService: UserService,
    private val jwtService: JWTService,
    private val passwordEncoder: PasswordEncoder,
    private val tokenService: TokenService,
    private val configurationService: ConfigurationService,
    private val emailTemplateService: EmailTemplateService,
    private val emailService: EmailService
) {

    data class TokenPair(
        val accessToken: String,
        val refreshToken: String
    )

    @Transactional
    fun login(
        request: LoginUserRequest
    ): TokenPair {
        val user = userService.getByEmailOrUsernameOrNull(request.emailOrUsername)
            ?: throw HttpException.Unauthorized("Invalid username or password.")

        if (!passwordEncoder.matches(request.password, user.passwordHash))
            throw HttpException.Unauthorized("Invalid username or password.")

        val accessToken = jwtService.generate(user.id, JWTService.Type.ACCESS)
        val refreshToken = jwtService.generate(user.id, JWTService.Type.REFRESH)

        return TokenPair(accessToken, refreshToken)
    }

    @Transactional
    fun refresh(
        refreshToken: String
    ): TokenPair {
        if (jwtService.isExpired(refreshToken))
            throw HttpException.Unauthorized("Expired refresh token.")

        val data = jwtService.getData(refreshToken)
            ?: throw HttpException.Unauthorized("Invalid refresh token.")

        if (data.type != JWTService.Type.REFRESH)
            throw HttpException.Unauthorized("Invalid refresh token.")

        if (userService.getById(data.subject) == null)
            throw HttpException.Unauthorized("Invalid refresh token.")

        val accessToken = jwtService.generate(data.subject, JWTService.Type.ACCESS)
        val refreshToken = jwtService.generate(data.subject, JWTService.Type.REFRESH)

        return TokenPair(accessToken, refreshToken)
    }

    @Transactional
    fun requestVerification(user: UserEntity) {
        if (user.status != UserEntity.Status.PENDING_VERIFICATION)
            return

        val duration = configurationService.getTyped<Duration>(Key.USER_VERIFICATION_DURATION)

        val token = tokenService.create(
            type = Type.VERIFICATION,
            duration = duration
        )

        val updatedUser = userService.addToken(user, token)

        val emailTemplate = emailTemplateService.getByType(EmailTemplateEntity.Type.USER_VERIFICATION)
            ?: throw HttpException.NotFound()

        val context = Context().apply {
            val fqdn = configurationService.getTyped<String>(Key.GENERAL_FQDN)
            val duration = configurationService.getTyped<Duration>(Key.USER_VERIFICATION_DURATION)

            setVariable("user", updatedUser)
            setVariable("url", "https://$fqdn/api/auth/verify?uuid=${token.uuid}")
            setVariable("duration", duration)
            setVariable("expiration", updatedUser.createdAt.plusMillis(duration.toMillis()))
        }

        emailService.send(updatedUser.email, emailTemplate, context)
    }

}