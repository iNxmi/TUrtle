package de.csw.turtle.api.controller

import de.csw.turtle.api.dto.patch.PatchProfileRequest
import de.csw.turtle.api.dto.get.GetUserResponse
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.mapper.UserMapper
import de.csw.turtle.api.security.Permission.*
import de.csw.turtle.api.security.RequiresPermission
import de.csw.turtle.api.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/profile")
class ProfileController(
    private val userService: UserService,
    private val userMapper: UserMapper
) {

    @RequiresPermission(API_PROFILE_GET)
    @GetMapping
    fun get(@AuthenticationPrincipal user: UserEntity) = ResponseEntity.ok(userMapper.get(user))

    @RequiresPermission(API_PROFILE_PATCH)
    @PatchMapping
    fun patch(
        @AuthenticationPrincipal user: UserEntity,
        @RequestBody patchProfileRequest: PatchProfileRequest
    ): ResponseEntity<GetUserResponse> {
        val updated = userService.updateProfile(user.username, patchProfileRequest)
        return ResponseEntity.ok(userMapper.get(updated))
    }

    @RequiresPermission(API_PROFILE_DELETE)
    @DeleteMapping
    fun delete(
        @AuthenticationPrincipal user: UserEntity
    ): ResponseEntity<GetUserResponse> {
        userService.delete(user.id)
        return ResponseEntity.noContent().build()
    }

}