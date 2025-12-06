package de.csw.turtle.api.controller.api

import de.csw.turtle.api.Permission.*
import de.csw.turtle.api.dto.LoginUserRequest
import de.csw.turtle.api.dto.RegisterUserRequest
import de.csw.turtle.api.dto.get.GetUserResponse
import de.csw.turtle.api.mapper.UserMapper
import de.csw.turtle.api.service.AuthService
import de.csw.turtle.api.service.SecurityService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService,
    private val userMapper: UserMapper,
    private val securityService: SecurityService
) {

    @PostMapping("/register")
    fun register(
        @RequestBody request: RegisterUserRequest
    ): ResponseEntity<GetUserResponse> {
        securityService.check(BACKEND__API_AUTH__REGISTER)

        val user = authService.register(request)

        return ResponseEntity
            .created(URI.create("/api/users/${user.username}"))
            .body(userMapper.get(user))
    }

    @PostMapping("/login")
    fun login(
        @RequestBody loginUserRequest: LoginUserRequest,
        httpRequest: HttpServletRequest
    ): ResponseEntity<GetUserResponse> {
        securityService.check(BACKEND__API_AUTH__LOGIN)

        val user = authService.login(loginUserRequest, httpRequest)
        return ResponseEntity.ok(userMapper.get(user))
    }

    @GetMapping("/logout")
    fun logout(
        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse,
        authentication: Authentication
    ): ResponseEntity<Void> {
        securityService.check(BACKEND__API_AUTH__LOGOUT)

        authService.logout(httpRequest, httpResponse, authentication)
        return ResponseEntity.noContent().build()
    }

}