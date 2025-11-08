package de.csw.turtle.api.v1.controller

import de.csw.turtle.api.v1.dto.request.LoginUserRequest
import de.csw.turtle.api.v1.dto.request.RegisterUserRequest
import de.csw.turtle.api.v1.dto.response.GetUserResponse
import de.csw.turtle.api.v1.entity.UserEntity
import de.csw.turtle.api.v1.exception.CorruptAuthenticationException
import de.csw.turtle.api.v1.exception.EmailAlreadyExistsException
import de.csw.turtle.api.v1.exception.StudentIdAlreadyExistsException
import de.csw.turtle.api.v1.exception.UnauthorizedException
import de.csw.turtle.api.v1.exception.UserNotFoundException
import de.csw.turtle.api.v1.exception.UsernameAlreadyExistsException
import de.csw.turtle.api.v1.exception.UsernameOrPasswordInvalidException
import de.csw.turtle.api.v1.repository.UserRepository
import de.csw.turtle.api.v1.service.PasswordEncoderService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    val userRepository: UserRepository,
    val passwordEncoderService: PasswordEncoderService,
    val authenticationManager: AuthenticationManager
) {

    @PostMapping("/register")
    fun register(
        @RequestBody request: RegisterUserRequest
    ): ResponseEntity<GetUserResponse> {
        if (userRepository.findByUserName(request.username) != null)
            throw UsernameAlreadyExistsException(request.username)

        if (userRepository.findByEmail(request.email) != null)
            throw EmailAlreadyExistsException(request.email)

        if (userRepository.findByStudentId(request.studentId) != null)
            throw StudentIdAlreadyExistsException(request.studentId)

        val passwordHash = passwordEncoderService.encode(request.password)
        val user = UserEntity(
            request.username,
            request.firstName,
            request.lastName,
            request.email,
            request.studentId,
            passwordHash
        )
        userRepository.save(user)

        return ResponseEntity
            .created(URI.create("/api/v1/users/${user.userName}"))
            .body(GetUserResponse(user))
    }

    @PostMapping("/login")
    fun login(
        @RequestBody loginUserRequest: LoginUserRequest,
        httpRequest: HttpServletRequest
    ): ResponseEntity<GetUserResponse> {
        val user = userRepository.findByUserName(loginUserRequest.username)
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

        val getUserResponse = GetUserResponse(user)
        return ResponseEntity.ok(getUserResponse)
    }

    @GetMapping("/logout")
    fun logout(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ): ResponseEntity<GetUserResponse> {
        val logoutHandler = SecurityContextLogoutHandler()
        logoutHandler.logout(request, response, authentication)

        return ResponseEntity.noContent().build()
    }

    @GetMapping("/me")
    fun me(@AuthenticationPrincipal user: UserEntity): ResponseEntity<GetUserResponse> {
        val getUserResponse = GetUserResponse(user)
        return ResponseEntity.ok(getUserResponse)
    }

}