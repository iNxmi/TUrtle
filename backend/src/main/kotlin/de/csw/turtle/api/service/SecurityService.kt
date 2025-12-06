package de.csw.turtle.api.service

import de.csw.turtle.api.Permission
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.exceptions.security.InsufficientPermissionException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class SecurityService(
    private val roleService: RoleService,
    private val userService: UserService
) {

    fun check(permission: Permission): UserEntity? {
        val authentication = SecurityContextHolder.getContext().authentication
            ?: throw IllegalStateException("Authentication not found.")

        val userDetails = authentication.principal as? UserDetails

        val user = if (userDetails != null) {
            userService.get(userDetails.username)
        } else null

        val permissions = if (user != null) {
            user.roles.flatMap { it.permissions }
        } else {
            roleService.getByName("Guest")!!.permissions
        }

        if (!permissions.contains(permission))
            throw InsufficientPermissionException(permission)

        return user
    }

}