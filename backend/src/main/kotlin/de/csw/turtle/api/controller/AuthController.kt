package de.csw.turtle.api.controller

import de.csw.turtle.api.dto.LoginUserRequest
import de.csw.turtle.api.dto.RegisterUserRequest
import de.csw.turtle.api.dto.get.GetUserResponse
import de.csw.turtle.api.mapper.UserMapper
import de.csw.turtle.api.service.AuthService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService,
    private val userMapper: UserMapper
) {

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('api.auth:register')")
    fun register(
        @RequestBody request: RegisterUserRequest
    ): ResponseEntity<GetUserResponse> {
        val user = authService.register(request)

        return ResponseEntity
            .created(URI.create("/api/users/${user.userName}"))
            .body(userMapper.get(user))
    }

    @PostMapping("/login")
    @PreAuthorize("hasAuthority('api.auth:login')")
    fun login(
        @RequestBody loginUserRequest: LoginUserRequest,
        httpRequest: HttpServletRequest
    ): ResponseEntity<GetUserResponse> {
        val user = authService.login(loginUserRequest, httpRequest)
        return ResponseEntity.ok(userMapper.get(user))
    }

    @GetMapping("/logout")
    @PreAuthorize("hasAuthority('api.auth:logout')")
    fun logout(
        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse,
        authentication: Authentication
    ): ResponseEntity<Void> {
        authService.logout(httpRequest, httpResponse, authentication)
        return ResponseEntity.noContent().build()
    }

}