package de.csw.turtle.api.v1.controller

import de.csw.turtle.api.v1.dto.response.GetUserResponse
import de.csw.turtle.api.v1.exception.UserNotFoundException
import de.csw.turtle.api.v1.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val repository: UserRepository
) {

    @GetMapping
    fun getPaginated(
        pageable: Pageable = PageRequest.of(0, 20)
    ): ResponseEntity<Page<GetUserResponse>> {
        val page = repository.findAll(pageable)
            .map { GetUserResponse(it) }

        return ResponseEntity.ok(page)
    }

    @GetMapping("/{username}")
    fun getByUsername(@PathVariable username: String): ResponseEntity<GetUserResponse> {
        val user = repository.findByUsername(username)
            ?: throw UserNotFoundException(username)

        val response = GetUserResponse(user)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{username}")
    fun deleteByUsername(@PathVariable username: String): ResponseEntity<Void> {
        val user = repository.findByUsername(username)
            ?: throw UserNotFoundException(username)

        repository.delete(user)

        return ResponseEntity.noContent().build()
    }

}