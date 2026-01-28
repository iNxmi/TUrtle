package de.csw.turtle.api.controller.api

import de.csw.turtle.api.dto.LoginUserRequest
import de.csw.turtle.api.dto.RegisterUserRequest
import de.csw.turtle.api.dto.get.GetUserResponse
import de.csw.turtle.api.mapper.UserMapper
import de.csw.turtle.api.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService,
    private val userMapper: UserMapper
) {

    @PostMapping("/register")
    fun register(
        @RequestBody request: RegisterUserRequest
    ): ResponseEntity<GetUserResponse> {
        val entity = authService.register(request)
        val dto = userMapper.get(entity)
        val location = URI.create("/api/users/${entity.id}")
        return ResponseEntity.created(location).body(dto)
    }

    @PostMapping("/login")
    fun login(
        @RequestBody loginUserRequest: LoginUserRequest
    ): ResponseEntity<Map<String, String>> {
        val token = authService.login(loginUserRequest)
        return ResponseEntity.ok(mapOf("token" to token))
    }

}