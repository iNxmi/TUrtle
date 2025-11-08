package de.csw.turtle.api.v1.controller

import de.csw.turtle.api.v1.dto.request.CreateUserRequest
import de.csw.turtle.api.v1.dto.request.UpdateUserRequest
import de.csw.turtle.api.v1.dto.response.GetUserResponse
import de.csw.turtle.api.v1.entity.UserEntity
import de.csw.turtle.api.v1.exception.exceptions.UserNotFoundException
import de.csw.turtle.api.v1.exception.exceptions.UsernameAlreadyExistsException
import de.csw.turtle.api.v1.repository.UserRepository
import de.csw.turtle.api.v1.service.PasswordEncoderService
import de.csw.turtle.api.v1.service.UserService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.security.core.session.SessionRegistry
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService
) {

    @PostMapping
    fun create(
        @RequestBody createUserRequest: CreateUserRequest
    ): ResponseEntity<GetUserResponse> {
        val user = userService.create(createUserRequest)
        return ResponseEntity
            .created(URI.create("/api/v1/users/${user.username}"))
            .body(GetUserResponse(user))
    }

    @GetMapping
    fun getPaginated(
        @RequestParam(name = "page", defaultValue = "0") page: Int,
        @RequestParam(name = "size", defaultValue = "20") size: Int
    ): ResponseEntity<Page<GetUserResponse>> {
        val page = userService.getAllPaged(page, size).map { GetUserResponse(it) }
        return ResponseEntity.ok(page)
    }

    @GetMapping("/{username}")
    fun getByUsername(
        @PathVariable username: String
    ): ResponseEntity<GetUserResponse> {
        val user = userService.getByUsername(username)
        return ResponseEntity.ok(GetUserResponse(user))
    }

    @PatchMapping("/{username}")
    fun patchByUsername(
        @PathVariable username: String,
        @RequestBody updateUserRequest: UpdateUserRequest
    ): ResponseEntity<GetUserResponse> {
        val user = userService.update(username, updateUserRequest)
        return ResponseEntity.ok(GetUserResponse(user))
    }

    @DeleteMapping("/{username}")
    fun deleteByUsername(
        @PathVariable username: String
    ): ResponseEntity<Void> {
        userService.delete(username)
        return ResponseEntity.noContent().build()
    }

}