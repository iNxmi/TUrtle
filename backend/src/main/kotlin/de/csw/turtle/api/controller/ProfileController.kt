package de.csw.turtle.api.controller

import de.csw.turtle.api.Permission.*
import de.csw.turtle.api.dto.get.GetUserResponse
import de.csw.turtle.api.dto.patch.PatchProfileRequest
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.mapper.UserMapper
import de.csw.turtle.api.service.SecurityService
import de.csw.turtle.api.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/profile")
class ProfileController(
    private val userService: UserService,
    private val userMapper: UserMapper,
    private val securityService: SecurityService
) {

    @GetMapping
    fun get(@AuthenticationPrincipal user: UserEntity): ResponseEntity<GetUserResponse> {
        securityService.required(API_PROFILE__GET)

        return ResponseEntity.ok(userMapper.get(user))
    }

    @PatchMapping
    fun patch(
        @AuthenticationPrincipal user: UserEntity,
        @RequestBody patchProfileRequest: PatchProfileRequest
    ): ResponseEntity<GetUserResponse> {
        securityService.required(API_PROFILE__PATCH)

        val updated = userService.updateProfile(user.username, patchProfileRequest)
        return ResponseEntity.ok(userMapper.get(updated))
    }

    @DeleteMapping
    fun delete(
        @AuthenticationPrincipal user: UserEntity
    ): ResponseEntity<GetUserResponse> {
        securityService.required(API_PROFILE__DELETE)

        userService.delete(user.id)
        return ResponseEntity.noContent().build()
    }

}