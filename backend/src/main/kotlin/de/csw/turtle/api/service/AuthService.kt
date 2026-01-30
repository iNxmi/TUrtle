package de.csw.turtle.api.service

import de.csw.turtle.api.dto.LoginUserRequest
import de.csw.turtle.api.dto.RegisterUserRequest
import de.csw.turtle.api.dto.create.CreateUserRequest
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.UnauthorizedException
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

    @Transactional
    fun register(
        registerUserRequest: RegisterUserRequest
    ): UserEntity {
        val createUserRequest = CreateUserRequest(
            username = registerUserRequest.username,
            firstName = registerUserRequest.firstName,
            lastName = registerUserRequest.lastName,
            email = registerUserRequest.email,
            emojis = registerUserRequest.emojis,
            password = registerUserRequest.password,
        )

        return userService.create(createUserRequest)
    }

    @Transactional
    fun login(
        request: LoginUserRequest
    ): String {
        try {
            val credentials = UsernamePasswordAuthenticationToken.unauthenticated(
                request.username,
                request.password
            )

            authenticationManager.authenticate(credentials)
        } catch (_: org.springframework.security.authentication.BadCredentialsException) {
            throw UnauthorizedException("Invalid username or password.")
        }

        val userDetails = customUserDetailsService.loadUserByUsername(request.username)
        return jwtService.generate(userDetails)
    }

}