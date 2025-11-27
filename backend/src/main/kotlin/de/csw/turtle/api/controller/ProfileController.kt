package de.csw.turtle.api.controller

import de.csw.turtle.api.dto.get.GetUserResponse
import de.csw.turtle.api.dto.patch.PatchProfileRequest
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.mapper.UserMapper
import de.csw.turtle.api.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/profile")
class ProfileController(
    private val userService: UserService,
    private val userMapper: UserMapper
) {

    @GetMapping
    @PreAuthorize("hasAuthority('api.profile:get')")
    fun get(@AuthenticationPrincipal user: UserEntity) = ResponseEntity.ok(userMapper.get(user))

    @PatchMapping
    @PreAuthorize("hasAuthority('api.profile:patch')")
    fun patch(
        @AuthenticationPrincipal user: UserEntity,
        @RequestBody patchProfileRequest: PatchProfileRequest
    ): ResponseEntity<GetUserResponse> {
        val updated = userService.updateProfile(user.username, patchProfileRequest)
        return ResponseEntity.ok(userMapper.get(updated))
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('api.profile:delete')")
    fun delete(
        @AuthenticationPrincipal user: UserEntity
    ): ResponseEntity<GetUserResponse> {
        userService.delete(user.id)
        return ResponseEntity.noContent().build()
    }

}