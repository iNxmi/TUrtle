package com.csw.turtleapi.api.v1.controller

import com.csw.turtleapi.api.v1.entity.User
import com.csw.turtleapi.api.v1.repository.UserRepository
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    private val userRepository: UserRepository
) {

    @GetMapping
    fun getAll(): ResponseEntity<Set<User>> {
        val result = userRepository.findAll().toSet()
        return ResponseEntity.ok(result)
    }

    @GetMapping("/{username}")
    fun getByUsername(@PathVariable username: String): ResponseEntity<User> {
        val result = userRepository.findByUsername(username).orElse(null)

        if (result != null)
            return ResponseEntity.ok(result)

        return ResponseEntity.notFound().build()
    }

    @PostMapping
    fun create(@RequestBody user: User): User? = userRepository.save(user)

    @DeleteMapping("/{username}")
    fun deleteByUsername(@PathVariable username: String) = userRepository.deleteByUsername(username)

}