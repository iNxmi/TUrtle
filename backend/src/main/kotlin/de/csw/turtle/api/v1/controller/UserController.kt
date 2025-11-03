package de.csw.turtle.api.v1.controller

import de.csw.turtle.api.v1.dto.request.CreateUserRequest
import de.csw.turtle.api.v1.dto.response.GetUserResponse
import de.csw.turtle.api.v1.entity.UserEntity
import de.csw.turtle.api.v1.exception.EmailAlreadyExistsException
import de.csw.turtle.api.v1.exception.StudentIdAlreadyExistsException
import de.csw.turtle.api.v1.exception.UserNotFoundException
import de.csw.turtle.api.v1.exception.UsernameAlreadyExistsException
import de.csw.turtle.api.v1.repository.UserRepository
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/users")
class UserController(
    private val repository: UserRepository
) {

    @GetMapping
    fun getAll(pageable: Pageable): ResponseEntity<Page<GetUserResponse>> {
        val page = repository.findAll(pageable).map {
            GetUserResponse(
                username = it.username,
                firstName = it.firstName,
                lastName = it.lastName,
                email = it.email,
                studentId = it.studentId
            )
        }
        return ResponseEntity.ok(page)
    }

    @GetMapping("/{username}")
    fun getByUsername(@PathVariable username: String): ResponseEntity<GetUserResponse> {
        val user = repository.findByUsername(username)
            ?: throw UserNotFoundException(username)

        val dto = GetUserResponse(
            username = user.username,
            firstName = user.firstName,
            lastName = user.lastName,
            email = user.email,
            studentId = user.studentId
        )

        return ResponseEntity.ok(dto)
    }

    @PostMapping
    fun create(@Valid @RequestBody request: CreateUserRequest): ResponseEntity<GetUserResponse> {
        if (repository.findByUsername(request.username) != null)
            throw UsernameAlreadyExistsException(request.username)
        if (repository.findByEmail(request.email) != null)
            throw EmailAlreadyExistsException(request.email)
        if (repository.findByStudentId(request.studentId) != null)
            throw StudentIdAlreadyExistsException(request.studentId)

        val user = UserEntity(
            username = request.username,
            firstName = request.firstName,
            lastName = request.lastName,
            email = request.email,
            studentId = request.studentId,
            passwordHash = request.password, // TODO needs to behashed
        )

        repository.save(user)

        val response = GetUserResponse(
            username = user.username,
            firstName = user.firstName,
            lastName = user.lastName,
            email = user.email,
            studentId = user.studentId
        )

        return ResponseEntity
            .created(URI.create("/users/${user.username}"))
            .body(response)
    }

    @DeleteMapping("/{username}")
    fun deleteByUsername(@PathVariable username: String): ResponseEntity<Void> {
        val user = repository.findByUsername(username)
            ?: throw UserNotFoundException(username)

        repository.delete(user)

        return ResponseEntity.noContent().build()
    }

}