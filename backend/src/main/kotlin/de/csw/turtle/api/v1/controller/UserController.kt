package de.csw.turtle.api.v1.controller

import de.csw.turtle.api.v1.dto.request.CreateUserRequest
import de.csw.turtle.api.v1.dto.request.UpdateUserRequest
import de.csw.turtle.api.v1.dto.response.GetUserResponse
import de.csw.turtle.api.v1.entity.UserEntity
import de.csw.turtle.api.v1.exception.UserNotFoundException
import de.csw.turtle.api.v1.exception.UsernameAlreadyExistsException
import de.csw.turtle.api.v1.repository.UserRepository
import de.csw.turtle.api.v1.service.PasswordEncoderService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.security.core.session.SessionRegistry
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userRepository: UserRepository,
    private val sessionRegistry: SessionRegistry,
    private val passwordEncoderService: PasswordEncoderService
) {

    @GetMapping
    fun getPaginated(
        @RequestParam(name = "page", defaultValue = "0") page: Int,
        @RequestParam(name = "size", defaultValue = "20") size: Int
    ): ResponseEntity<Page<GetUserResponse>> {
        val pageable = PageRequest.of(page, size)

        val page = userRepository.findAll(pageable)
            .map { GetUserResponse(it) }

        return ResponseEntity.ok(page)
    }

    @PostMapping
    fun create(
        @RequestBody createUserRequest: CreateUserRequest
    ): ResponseEntity<GetUserResponse> {
        if (userRepository.findByUserName(createUserRequest.username) != null)
            throw UsernameAlreadyExistsException(createUserRequest.username)

        val user: UserEntity = createUserRequest.create(passwordEncoderService.encoder)
        userRepository.save(user)

        val response = GetUserResponse(user)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{username}")
    fun getByUsername(
        @PathVariable username: String
    ): ResponseEntity<GetUserResponse> {
        val user = userRepository.findByUserName(username)
            ?: throw UserNotFoundException(username)

        val response = GetUserResponse(user)
        return ResponseEntity.ok(response)
    }

    @PatchMapping("/{username}")
    fun patchByUsername(
        @PathVariable username: String,
        @RequestBody updateUserRequest: UpdateUserRequest
    ): ResponseEntity<GetUserResponse> {
        val user = userRepository.findByUserName(username)
            ?: throw UserNotFoundException(username)

        updateUserRequest.username?.let { user.userName = it }
        updateUserRequest.firstName?.let { user.firstName = it }
        updateUserRequest.lastName?.let { user.lastName = it }
        updateUserRequest.email?.let { user.email = it }
        updateUserRequest.studentId?.let { user.studentId = it }
        updateUserRequest.password?.let { user.passwordHash = passwordEncoderService.encode(it) }
        userRepository.save(user)

        val response = GetUserResponse(user)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{username}")
    fun deleteByUsername(
        @PathVariable username: String
    ): ResponseEntity<Void> {
        val user = userRepository.findByUserName(username)
            ?: throw UserNotFoundException(username)

        userRepository.delete(user)
        //TODO make session invalidation work when deleting a user
        sessionRegistry.getAllSessions(user.username, false).forEach { it.expireNow() }

        return ResponseEntity.noContent().build()
    }

}