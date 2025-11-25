package de.csw.turtle.api.service

import de.csw.turtle.api.dto.create.CreateUserRequest
import de.csw.turtle.api.dto.LoginUserRequest
import de.csw.turtle.api.dto.RegisterUserRequest
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.exceptions.user.UsernameOrPasswordInvalidException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val userService: UserService,
    private val passwordEncoderService: PasswordEncoderService,
    private val authenticationManager: AuthenticationManager
) {

    @Transactional
    fun register(
        registerUserRequest: RegisterUserRequest
    ): UserEntity {
        val createUserRequest = CreateUserRequest(
            registerUserRequest.username,
            registerUserRequest.firstName,
            registerUserRequest.lastName,
            registerUserRequest.email,
            registerUserRequest.studentId,
            registerUserRequest.password,
        )

        val user = userService.create(createUserRequest)
        return user
    }

    @Transactional
    fun login(
        loginUserRequest: LoginUserRequest,
        httpRequest: HttpServletRequest
    ): UserEntity {
        val user = userService.get(loginUserRequest.username)
            ?: throw UsernameOrPasswordInvalidException()

        if (!passwordEncoderService.matches(loginUserRequest.password, user.password))
            throw UsernameOrPasswordInvalidException()

        val authenticationToken = UsernamePasswordAuthenticationToken.unauthenticated(
            loginUserRequest.username,
            loginUserRequest.password
        )

        val authentication = authenticationManager.authenticate(authenticationToken)
        SecurityContextHolder.getContext().authentication = authentication

        httpRequest.getSession(true).setAttribute(
            HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
            SecurityContextHolder.getContext()
        )

        return user
    }

    @Transactional
    fun logout(httpRequest: HttpServletRequest, httpResponse: HttpServletResponse, authentication: Authentication) {
        val logoutHandler = SecurityContextLogoutHandler()
        logoutHandler.logout(httpRequest, httpResponse, authentication)
    }

}