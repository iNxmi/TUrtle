package de.csw.turtle.api

import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.service.UserService
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class SimpleUserDetails(
    private val username: String,
    private val password: String,
    private val authorities: Collection<GrantedAuthority>,
) : UserDetails {
    override fun getUsername() = username
    override fun getPassword() = password
    override fun getAuthorities() = authorities

    fun hasPermission(permission: Permission): Boolean = authorities.any { it.authority == permission.toString() }
    fun getUser(service: UserService): UserEntity = service.getByUsername(username)
}