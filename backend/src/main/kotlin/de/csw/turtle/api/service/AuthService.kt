package de.csw.turtle.api.service

import de.csw.turtle.api.dto.auth.LoginUserRequest
import de.csw.turtle.api.dto.auth.RegisterUserRequest
import de.csw.turtle.api.entity.ConfigurationEntity.Key
import de.csw.turtle.api.entity.EmailTemplateEntity
import de.csw.turtle.api.entity.TokenEntity.Type
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.entity.UserEntity.Status
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

    data class Authentication(
        val user: UserEntity,
        val accessToken: String,
        val refreshToken: String
    )

    @Transactional
    fun login(
        request: LoginUserRequest
    ): Authentication {
        val user = userService.getByEmailOrUsernameOrNull(request.emailOrUsername)
            ?: throw HttpException.Unauthorized("Invalid username or password.")

        if (!passwordEncoder.matches(request.password, user.passwordHash))
            throw HttpException.Unauthorized("Invalid username or password.")

        val accessToken = jwtService.generate(user.id, JWTService.Type.ACCESS)
        val refreshToken = jwtService.generate(user.id, JWTService.Type.REFRESH)

        return Authentication(
            user = user,
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    @Transactional
    fun register(
        request: RegisterUserRequest
    ): Authentication {
        val user = userService.create(
            username = request.username,
            firstName = request.firstName,
            lastName = request.lastName,
            email = request.email,
            password = request.password,
            emojis = userService.generateEmojis(),
            status = Status.PENDING_VERIFICATION,
            roleIds = setOf()
        )

        val accessToken = jwtService.generate(user.id, JWTService.Type.ACCESS)
        val refreshToken = jwtService.generate(user.id, JWTService.Type.REFRESH)

        return Authentication(
            user = user,
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    @Transactional
    fun refresh(
        refreshToken: String
    ): Authentication {
        if (jwtService.isExpired(refreshToken))
            throw HttpException.Unauthorized("Expired refresh token.")

        val data = jwtService.getData(refreshToken)
            ?: throw HttpException.Unauthorized("Invalid refresh token.")

        if (data.type != JWTService.Type.REFRESH)
            throw HttpException.Unauthorized("Invalid refresh token.")

        val user = userService.getById(data.subject)
            ?: throw HttpException.Unauthorized("Invalid refresh token.")

        val accessToken = jwtService.generate(data.subject, JWTService.Type.ACCESS)
        val refreshToken = jwtService.generate(data.subject, JWTService.Type.REFRESH)

        return Authentication(
            user = user,
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    @Transactional
    fun requestVerification(user: UserEntity) {
        val duration = configurationService.getTyped<Duration>(Key.USER_VERIFICATION_DURATION)

        val existingToken = user.tokens.firstOrNull { it.type == Type.VERIFICATION }
        val token = existingToken ?: tokenService.create(
            type = Type.VERIFICATION,
            duration = duration
        )

        val updatedUser = if (existingToken == null) {
            userService.addToken(user, token)
        } else user

        val template = emailTemplateService.getByType(EmailTemplateEntity.Type.USER_VERIFICATION)
            ?: throw NoSuchElementException()

        val context = Context().apply {
            val duration = configurationService.getTyped<Duration>(Key.USER_VERIFICATION_DURATION)

            setVariable("user", updatedUser)
            setVariable("code", token.code)
            setVariable("duration", duration)
            setVariable("expiration", updatedUser.createdAt.plusMillis(duration.toMillis()))
        }

        emailService.send(updatedUser.email, template, context)
    }

}