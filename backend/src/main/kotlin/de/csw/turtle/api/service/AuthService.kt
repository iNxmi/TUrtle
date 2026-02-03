package de.csw.turtle.api.service

import de.csw.turtle.api.dto.LoginUserRequest
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
    private val jwtService: JWTService
) {

    data class TokenPair(
        val accessToken: String,
        val refreshToken: String
    )

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
        val user = userService.getByUsername(userDetails.username)

        if (!user.verified)
            throw HttpException.Forbidden("Please verify your account.")

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