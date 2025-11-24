package de.csw.turtle.api.controller

import de.csw.turtle.api.dto.request.PatchProfileRequest
import de.csw.turtle.api.dto.response.GetUserResponse
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.security.Permission.*
import de.csw.turtle.api.security.RequiresPermission
import de.csw.turtle.api.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/profile")
class ProfileController(
    private val userService: UserService
) {

    @RequiresPermission(API_PROFILE_GET)
    @GetMapping
    fun get(@AuthenticationPrincipal user: UserEntity) = ResponseEntity.ok(GetUserResponse(user))

    @RequiresPermission(API_PROFILE_PATCH)
    @PatchMapping
    fun patch(
        @AuthenticationPrincipal user: UserEntity,
        @RequestBody patchUserRequest: PatchProfileRequest
    ): ResponseEntity<GetUserResponse> {
        val updated = userService.update(user.username, patchUserRequest)
        return ResponseEntity.ok(GetUserResponse(updated))
    }

    @RequiresPermission(API_PROFILE_DELETE)
    @DeleteMapping
    fun delete(
        @AuthenticationPrincipal user: UserEntity
    ): ResponseEntity<GetUserResponse> {
        userService.delete(user.username)
        return ResponseEntity.noContent().build()
    }

}