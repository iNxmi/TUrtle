package de.csw.turtle.api.service

import de.csw.turtle.api.Permission
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.exceptions.security.InsufficientPermissionException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class SecurityService(
    private val userService: UserService,
    private val roleService: RoleService
) {

    fun hasPermission(permission: Permission): UserEntity? {
        val authentication = SecurityContextHolder.getContext().authentication
            ?: throw IllegalStateException("Authentication not found.")

        val principal = authentication.principal
        val permissions: Collection<Permission> = if (principal is UserDetails) {
            val user = userService.get(principal.username)
            user.roles.flatMap { it.permissions }
        } else {
            roleService.getByName("Anonymous")!!.permissions
        }

        if (!permissions.contains(permission))
            throw InsufficientPermissionException(permission)

        if(principal !is UserDetails)
            return null

        return userService.get(principal.username)
    }

}