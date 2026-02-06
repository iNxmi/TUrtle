package de.csw.turtle.api.service

import de.csw.turtle.api.dto.auth.LoginUserRequest
import de.csw.turtle.api.exception.HttpException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val userService: UserService,
    private val jwtService: JWTService,
    private val passwordEncoder: PasswordEncoder
) {

    data class TokenPair(
        val accessToken: String,
        val refreshToken: String
    )

    @Transactional
    fun login(
        request: LoginUserRequest
    ): TokenPair {
//        try {
//            val credentials = UsernamePasswordAuthenticationToken.unauthenticated(
//                request.emailOrUsername,
//                request.password
//            )
//
//            authenticationManager.authenticate(credentials)
//        } catch (_: org.springframework.security.authentication.BadCredentialsException) {
//            throw HttpException.Unauthorized("Invalid username or password.")
//        }

        val user = userService.getByEmailOrUsernameOrNull(request.emailOrUsername)
            ?: throw HttpException.Unauthorized("Invalid username or password.")

        if (!passwordEncoder.matches(request.password, user.password))
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
        if (data.type != JWTService.Type.REFRESH)
            throw HttpException.Unauthorized("Invalid refresh token.")

        if (userService.getById(data.subject) == null)
            throw HttpException.Unauthorized("Invalid refresh token.")

        val accessToken = jwtService.generate(data.subject, JWTService.Type.ACCESS)
        val refreshToken = jwtService.generate(data.subject, JWTService.Type.REFRESH)

        return TokenPair(accessToken, refreshToken)
    }

}