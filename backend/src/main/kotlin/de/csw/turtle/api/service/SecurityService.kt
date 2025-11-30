package de.csw.turtle.api.service

import de.csw.turtle.api.Permission
import de.csw.turtle.api.SimpleUserDetails
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.exceptions.security.InsufficientPermissionException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class SecurityService(
    private val userService: UserService
) {

    fun hasPermission(permission: Permission): UserEntity {
        val authentication = SecurityContextHolder.getContext().authentication
            ?: throw IllegalStateException("Authentication not found.")

        val principal = authentication.principal as SimpleUserDetails
        val user = userService.get(principal.username)
        val permissions = user.roles.flatMap { it.permissions }

        if (!permissions.contains(permission))
            throw InsufficientPermissionException(permission)

        return userService.get(principal.username)
    }

}