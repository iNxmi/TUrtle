package de.csw.turtle.api.v1.controller

import de.csw.turtle.api.v1.dto.request.RegisterUserRequest
import de.csw.turtle.api.v1.dto.response.GetUserResponse
import de.csw.turtle.api.v1.entity.UserEntity
import de.csw.turtle.api.v1.exception.EmailAlreadyExistsException
import de.csw.turtle.api.v1.exception.StudentIdAlreadyExistsException
import de.csw.turtle.api.v1.exception.UsernameAlreadyExistsException
import de.csw.turtle.api.v1.repository.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    val userRepository: UserRepository,
    val passwordEncoder: PasswordEncoder
) {

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterUserRequest): ResponseEntity<GetUserResponse> {
        if (userRepository.findByUsername(request.username) != null)
            throw UsernameAlreadyExistsException(request.username)

        if (userRepository.findByEmail(request.email) != null)
            throw EmailAlreadyExistsException(request.email)

        if (userRepository.findByStudentId(request.studentId) != null)
            throw StudentIdAlreadyExistsException(request.studentId)

        val passwordHash = passwordEncoder.encode(request.password)
        val user = UserEntity(request.username, request.firstName, request.lastName, request.email, request.studentId, passwordHash)
        userRepository.save(user)

        return ResponseEntity
            .created(URI.create("/api/v1/users/${user.username}"))
            .body(GetUserResponse(user))
    }

}