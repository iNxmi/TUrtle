package de.csw.turtle.api.controller

import de.csw.turtle.api.Permission
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.HttpException
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/permissions")
class PermissionController {

    @GetMapping
    fun getPermissions(
        @AuthenticationPrincipal user: UserEntity?
    ): ResponseEntity<SortedSet<Permission>> {
        if (user == null)
            throw HttpException.Unauthorized()

        val permissions = user.getAllPermissions()
        return ResponseEntity.ok(permissions.toSortedSet())
    }

}