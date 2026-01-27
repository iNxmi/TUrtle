package de.csw.turtle.api.controller.api

import de.csw.turtle.api.Permission.*
import de.csw.turtle.api.dto.get.GetUserResponse
import de.csw.turtle.api.dto.patch.PatchProfileRequest
import de.csw.turtle.api.mapper.UserMapper
import de.csw.turtle.api.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/profile")
class ProfileController(
    private val userService: UserService,
    private val userMapper: UserMapper
) {

    @GetMapping
    fun get(
        authentication: Authentication
    ): ResponseEntity<GetUserResponse> {
        println(authentication)
        TODO()
//        return ResponseEntity.ok(userMapper.get(user))
    }

    @PatchMapping
    fun patch(
        authentication: Authentication,
        @RequestBody patchProfileRequest: PatchProfileRequest
    ): ResponseEntity<GetUserResponse> {
        println(authentication)
        TODO()
//        val updated = userService.updateProfile(user.username, patchProfileRequest)
//        return ResponseEntity.ok(userMapper.get(updated))
    }

    @DeleteMapping
    fun delete(
        authentication: Authentication
    ): ResponseEntity<GetUserResponse> {
        println(authentication)
        TODO()
//        userService.delete(user.id)
//        return ResponseEntity.noContent().build()
    }

}