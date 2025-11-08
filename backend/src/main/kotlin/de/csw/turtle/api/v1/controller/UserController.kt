package de.csw.turtle.api.v1.controller

import de.csw.turtle.api.v1.dto.response.GetUserResponse
import de.csw.turtle.api.v1.exception.UserNotFoundException
import de.csw.turtle.api.v1.repository.UserRepository
import io.swagger.v3.oas.annotations.enums.ParameterIn
import org.hibernate.annotations.Parameter
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
        @RequestParam(required = false, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "20") size: Int
    ): ResponseEntity<Page<GetUserResponse>> {
        val pageable = PageRequest.of(page, size)

        val page = repository.findAll(pageable)
            .map { GetUserResponse(it) }

        return ResponseEntity.ok(page)
    }

    @GetMapping("/{username}")
    fun getByUsername(@PathVariable username: String): ResponseEntity<GetUserResponse> {
        val user = repository.findByUserName(username)
            ?: throw UserNotFoundException(username)

        val response = GetUserResponse(user)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{username}")
    fun deleteByUsername(@PathVariable username: String): ResponseEntity<Void> {
        val user = repository.findByUserName(username)
            ?: throw UserNotFoundException(username)

        repository.delete(user)

        return ResponseEntity.noContent().build()
    }

}