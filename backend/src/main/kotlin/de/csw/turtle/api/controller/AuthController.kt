package de.csw.turtle.api.controller

import de.csw.turtle.api.dto.request.LoginUserRequest
import de.csw.turtle.api.dto.request.RegisterUserRequest
import de.csw.turtle.api.dto.response.GetUserResponse
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.service.AuthService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/auth")
class AuthController(
    val authService: AuthService
) {

    @PostMapping("/register")
    fun register(
        @RequestBody request: RegisterUserRequest
    ): ResponseEntity<GetUserResponse> {
        val user = authService.register(request)

        return ResponseEntity
            .created(URI.create("/api/users/${user.userName}"))
            .body(GetUserResponse(user))
    }

    @PostMapping("/login")
    fun login(
        @RequestBody loginUserRequest: LoginUserRequest,
        httpRequest: HttpServletRequest
    ): ResponseEntity<GetUserResponse> {
        val user = authService.login(loginUserRequest, httpRequest)
        return ResponseEntity.ok(GetUserResponse(user))
    }

    @GetMapping("/logout")
    fun logout(
        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse,
        authentication: Authentication
    ): ResponseEntity<Void> {
        authService.logout(httpRequest, httpResponse, authentication)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/me")
    fun me(@AuthenticationPrincipal user: UserEntity): ResponseEntity<GetUserResponse> {
        val getUserResponse = GetUserResponse(user)
        return ResponseEntity.ok(getUserResponse)
    }

}