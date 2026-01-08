package de.csw.turtle.api.service

import de.csw.turtle.api.Permission
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.exceptions.auth.InsufficientPermissionException
import de.csw.turtle.api.exception.exceptions.auth.UnauthorizedException
import org.springframework.stereotype.Service

@Service
class PermissionService(
    private val roleService: RoleService,
    private val authService: AuthService
) {

    fun getPermissions(user: UserEntity?) = if (user != null) {
        user.roles.flatMap { it.permissions }
    } else {
        roleService.getByName("Guest")!!.permissions
    }.toSet()

    fun check(permission: Permission): UserEntity? {
        val user = authService.getAuthenticatedUser()
            ?: throw UnauthorizedException()

        val permissions = getPermissions(user)

        if (!permissions.contains(permission))
            throw InsufficientPermissionException(permission)

        return user
    }

}