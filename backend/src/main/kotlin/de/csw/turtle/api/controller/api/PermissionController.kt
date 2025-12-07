package de.csw.turtle.api.controller.api

import de.csw.turtle.api.Permission
import de.csw.turtle.api.service.AuthService
import de.csw.turtle.api.service.PermissionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/permissions")
class PermissionController(
    private val authService: AuthService,
    private val permissionService: PermissionService,
) {

    @GetMapping
    fun getPermissions(): ResponseEntity<SortedSet<Permission>> {
        val user = authService.getAuthenticatedUser()
        val permissions = permissionService.getPermissions(user)
        return ResponseEntity.ok(permissions.toSortedSet())
    }

}