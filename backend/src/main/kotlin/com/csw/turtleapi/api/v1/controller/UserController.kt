package com.csw.turtleapi.api.v1.controller

import com.csw.turtleapi.api.v1.entity.User
import com.csw.turtleapi.api.v1.repository.UserRepository
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
    fun getAllUsers(): Set<User> = userRepository.findAll().toSet()

    @GetMapping("/{username}")
    fun getUserByUsername(@PathVariable username: String): User? = userRepository.findByUsername(username).orElse(null)

    @PostMapping
    fun createUser(@RequestBody user: User): User? = userRepository.save(user)

    @DeleteMapping("/{id}")
    fun deleteUserById(@PathVariable id: Long) = userRepository.deleteById(id)

}