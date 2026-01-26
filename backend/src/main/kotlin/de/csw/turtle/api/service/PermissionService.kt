package de.csw.turtle.api.service

import de.csw.turtle.api.Permission
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.ForbiddenException
import de.csw.turtle.api.exception.UnauthorizedException
import org.springframework.stereotype.Service

@Service
class PermissionService(
    private val roleService: RoleService,
    private val authService: AuthService
) {

    fun getPermissions(user: UserEntity?) = if (user != null) {
        user.roles.flatMap { it.permissions }
    } else {
        roleService.getByName("Guest").permissions
    }.toSet()

    fun check(permission: Permission): UserEntity {
        val user = authService.getAuthenticatedUser()
            ?: throw UnauthorizedException("You must be logged in to access this resource.")

        val permissions = getPermissions(user)

        if (!permissions.contains(permission))
            throw ForbiddenException("You need '$permission' to access this resource.")

        return user
    }

}