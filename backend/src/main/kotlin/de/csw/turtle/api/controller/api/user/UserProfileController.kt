package de.csw.turtle.api.controller.api.user

import de.csw.turtle.api.Permission
import de.csw.turtle.api.dto.get.GetUserResponse
import de.csw.turtle.api.dto.patch.PatchProfileRequest
import de.csw.turtle.api.mapper.UserMapper
import de.csw.turtle.api.service.SecurityService
import de.csw.turtle.api.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user/profile")
class UserProfileController(
    private val userService: UserService,
    private val userMapper: UserMapper,
    private val securityService: SecurityService
) {

    @GetMapping
    fun get(
        @AuthenticationPrincipal principal: UserDetails
    ): ResponseEntity<GetUserResponse> {
        securityService.hasPermission(Permission.BACKEND__API_USER_PROFILE__GET)

        val user = userService.get(principal.username)
        return ResponseEntity.ok(userMapper.get(user))
    }

    @PatchMapping
    fun patch(
        @AuthenticationPrincipal principal: UserDetails,
        @RequestBody patchProfileRequest: PatchProfileRequest
    ): ResponseEntity<GetUserResponse> {
        securityService.hasPermission(Permission.BACKEND__API_USER_PROFILE__PATCH)

        val user = userService.get(principal.username)
        val updated = userService.updateProfile(user.username, patchProfileRequest)
        return ResponseEntity.ok(userMapper.get(updated))
    }

    @DeleteMapping
    fun delete(
        @AuthenticationPrincipal principal: UserDetails
    ): ResponseEntity<GetUserResponse> {
        securityService.hasPermission(Permission.BACKEND__API_USER_PROFILE__DELETE)

        val user = userService.get(principal.username)
        userService.delete(user.id)
        return ResponseEntity.noContent().build()
    }

}