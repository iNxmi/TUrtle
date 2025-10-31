package de.csw.turtle.v1.controller

import de.csw.turtle.v1.entity.User
import de.csw.turtle.v1.exception.UserNotFoundException
import de.csw.turtle.v1.exception.UsernameAlreadyExistsException
import de.csw.turtle.v1.repository.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.collections.toSet

@RestController
@RequestMapping("/v1/users")
class UserController(
    private val repository: UserRepository
) {

    @GetMapping
    fun getAll(): ResponseEntity<Set<User>> {
        val users = repository.findAll().toSet()
        return ResponseEntity.ok(users)
    }

    @GetMapping("/{username}")
    fun getByUsername(@PathVariable username: String): ResponseEntity<User> {
        val user = repository
            .findByUsername(username)
            .orElseThrow { UserNotFoundException(username) }

        return ResponseEntity.ok(user)
    }

    @PostMapping
    fun create(@RequestBody user: User): ResponseEntity<User> {
        repository.findByUsername(user.username)
            .ifPresent { UsernameAlreadyExistsException(user.username) }

        repository.save(user)

        return ResponseEntity.ok(user)
    }

    @DeleteMapping("/{username}")
    fun deleteByUsername(@PathVariable username: String) {
        val user = repository
            .findByUsername(username)
            .orElseThrow { UserNotFoundException(username) }

        repository.delete(user)
    }

}