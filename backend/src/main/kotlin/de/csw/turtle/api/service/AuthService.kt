package de.csw.turtle.api.service

import de.csw.turtle.api.dto.auth.LoginUserRequest
import de.csw.turtle.api.dto.auth.RegisterUserRequest
import de.csw.turtle.api.entity.ConfigurationEntity.Key
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.entity.UserEntity.Status
import de.csw.turtle.api.entity.VerificationSessionEntity.Type
import de.csw.turtle.api.exception.HttpException
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseCookie
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import java.util.*

private const val COOKIE_NAME_ACCESS_TOKEN = "access_token"
private const val COOKIE_NAME_REFRESH_TOKEN = "refresh_token"

@Service
class AuthService(
    private val userService: UserService,
    private val jwtService: JWTService,
    private val passwordEncoder: PasswordEncoder,
    private val verificationSessionService: VerificationSessionService,
    private val configurationService: ConfigurationService
) {

    private fun addCookie(name: String, value: String, duration: Duration?, response: HttpServletResponse) {
        val cookie = ResponseCookie.from(name, value)
            .httpOnly(true)
//            .secure(true) TODO uncomment to enable https only
            .path("/")
            .sameSite("Strict")
            .maxAge(duration ?: Duration.ofSeconds(-1))
            .build()

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString())
    }

    private fun deleteCookie(name: String, response: HttpServletResponse) = addCookie(
        name = name,
        value = "",
        duration = Duration.ZERO,
        response = response
    )

    private fun getDuration(type: JWTService.Type) = configurationService.getTyped<Duration>(type.key)

    data class Authentication(
        val user: UserEntity,
        val session: UUID? = null
    )

    @Transactional
    fun login(
        request: LoginUserRequest,
        response: HttpServletResponse
    ): Authentication {
        val user = userService.getByEmailOrUsernameOrNull(request.emailOrUsername)
            ?: throw HttpException.Unauthorized("Invalid username or password.")

        if (!passwordEncoder.matches(request.password, user.passwordHash))
            throw HttpException.Unauthorized("Invalid username or password.")

        if (user.status == Status.PENDING_VERIFICATION) {
            val session = verificationSessionService.get(user, Type.VERIFICATION)!!

            return Authentication(
                user = user,
                session = session.uuid
            )
        }

        val accessToken = jwtService.generate(user.id, JWTService.Type.ACCESS)
        val refreshToken = jwtService.generate(user.id, JWTService.Type.REFRESH)

        val refreshTokenDuration = if (request.rememberMe) {
            getDuration(JWTService.Type.REFRESH)
        } else null

        addCookie(COOKIE_NAME_ACCESS_TOKEN, accessToken, getDuration(JWTService.Type.ACCESS), response)
        addCookie(COOKIE_NAME_REFRESH_TOKEN, refreshToken, refreshTokenDuration, response)

        return Authentication(user)
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

        //TODO make duration system configuration
        val verificationSession = verificationSessionService.create(
            user = user,
            type = Type.VERIFICATION,
            duration = Duration.ofMinutes(10),
        )

        return Authentication(
            user = user,
            session = verificationSession.uuid
        )
    }

    @Transactional
    fun refresh(
        token: String,
        response: HttpServletResponse
    ): Authentication {
        if (jwtService.isExpired(token))
            throw HttpException.Unauthorized("Expired refresh token.")

        val data = jwtService.getData(token)
            ?: throw HttpException.Unauthorized("Invalid refresh token.")

        if (data.type != JWTService.Type.REFRESH)
            throw HttpException.Unauthorized("Invalid refresh token.")

        val user = userService.getById(data.subject)
            ?: throw HttpException.Unauthorized("Invalid refresh token.")

        if (user.status == Status.PENDING_VERIFICATION) {
            val verificationSession = verificationSessionService.get(user, Type.VERIFICATION)!!

            return Authentication(
                user = user,
                session = verificationSession.uuid
            )
        } else {
            val accessToken = jwtService.generate(user.id, JWTService.Type.ACCESS)
            val refreshToken = jwtService.generate(user.id, JWTService.Type.REFRESH)

            addCookie(COOKIE_NAME_ACCESS_TOKEN, accessToken, getDuration(JWTService.Type.ACCESS), response)
            addCookie(COOKIE_NAME_REFRESH_TOKEN, refreshToken, getDuration(JWTService.Type.REFRESH), response)

            return Authentication(
                user = user,
                session = null
            )
        }
    }

    fun logout(response: HttpServletResponse) {
        deleteCookie(COOKIE_NAME_ACCESS_TOKEN, response)
        deleteCookie(COOKIE_NAME_REFRESH_TOKEN, response)
    }

    @Transactional
    fun verify(uuid: UUID, code: String) {
        val session = verificationSessionService.getByUuid(uuid)
            ?: throw HttpException.NotFound()

        val user = session.user
        if (user.status != Status.PENDING_VERIFICATION)
            throw HttpException.Forbidden()

        if (session.type != Type.VERIFICATION)
            throw HttpException.BadRequest("Invalid session.")

        if (session.isExpired())
            throw HttpException.Unauthorized("Session expired.")

        //TODO change to system configuration
        val maxAttempts = 5
        if (session.attempts >= maxAttempts)
            throw HttpException.Unauthorized("Max attempts reached.")

        verificationSessionService.patch(session.id, session.attempts + 1)

        if (!passwordEncoder.matches(code, session.codeHash))
            throw HttpException.Forbidden("Wrong code.")

        val regexes = configurationService.getTyped<List<String>>(Key.USER_EMAIL_TRUSTED).map { Regex(it) }
        val isTrustedEmail = regexes.any { it.matches(user.email) }

        userService.patch(
            id = user.id,
            status = if (isTrustedEmail) Status.ACTIVE else Status.PENDING_APPROVAL
        )
        verificationSessionService.delete(session.id)
    }

}