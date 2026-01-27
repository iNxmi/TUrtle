package de.csw.turtle.api.controller.api

import de.csw.turtle.api.Permission.*
import de.csw.turtle.api.dto.get.GetUserResponse
import de.csw.turtle.api.dto.patch.PatchProfileRequest
import de.csw.turtle.api.mapper.UserMapper
import de.csw.turtle.api.service.PermissionService
import de.csw.turtle.api.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user/profile")
class ProfileController(
    private val userService: UserService,
    private val userMapper: UserMapper,
    private val permissionService: PermissionService
) {

    @GetMapping
    fun get(): ResponseEntity<GetUserResponse> {
        val user = permissionService.check(BACKEND__API_USER_PROFILE__GET)
        return ResponseEntity.ok(userMapper.get(user))
    }

    @PatchMapping
    fun patch(
        @RequestBody patchProfileRequest: PatchProfileRequest
    ): ResponseEntity<GetUserResponse> {
        val user = permissionService.check(BACKEND__API_USER_PROFILE__PATCH)
        val updated = userService.updateProfile(user.username, patchProfileRequest)
        return ResponseEntity.ok(userMapper.get(updated))
    }

    @DeleteMapping
    fun delete(): ResponseEntity<GetUserResponse> {
        val user = permissionService.check(BACKEND__API_USER_PROFILE__DELETE)
        userService.delete(user.id)
        return ResponseEntity.noContent().build()
    }

}