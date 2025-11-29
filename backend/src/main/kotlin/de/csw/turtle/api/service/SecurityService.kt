package de.csw.turtle.api.service

import de.csw.turtle.api.Permission
import de.csw.turtle.api.exception.exceptions.security.InsufficientPermissionException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class SecurityService(
    private val userService: UserService,
    private val roleService: RoleService
) {

    fun required(permission: Permission) {
        val authentication = SecurityContextHolder.getContext().authentication
            ?: TODO("implement exception")

        val principal = authentication.principal
        val permissions: Collection<Permission> = if (principal is UserDetails) {
            val user = userService.get(principal.username)
            user.roles.flatMap { it.permissions }
        } else {
            roleService.getByName("Anonymous")!!.permissions
        }

        if (!permissions.contains(permission))
            throw InsufficientPermissionException(permission)
    }

}