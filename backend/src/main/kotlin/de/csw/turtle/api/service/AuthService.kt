package de.csw.turtle.api.service

import de.csw.turtle.api.dto.LoginUserRequest
import de.csw.turtle.api.dto.RegisterUserRequest
import de.csw.turtle.api.dto.create.CreateUserRequest
import de.csw.turtle.api.entity.UserEntity
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

        return userService.create(createUserRequest)
    }

    @Transactional
    fun login(
        request: LoginUserRequest,
        httpRequest: HttpServletRequest
    ): UserEntity {
        val token = UsernamePasswordAuthenticationToken.unauthenticated(
            request.username,
            request.password
        )

        val authentication = authenticationManager.authenticate(token)
        SecurityContextHolder.getContext().authentication = authentication

        httpRequest.getSession(true).setAttribute(
            HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
            SecurityContextHolder.getContext()
        )

        return userService.get(request.username)
    }

    @Transactional
    fun logout(httpRequest: HttpServletRequest, httpResponse: HttpServletResponse, authentication: Authentication) =
        SecurityContextLogoutHandler().logout(httpRequest, httpResponse, authentication)

}