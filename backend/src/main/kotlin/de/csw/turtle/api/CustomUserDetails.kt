package de.csw.turtle.api

import de.csw.turtle.api.entity.UserEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(
    val userId: Long,
    private val username: String,
    private val password: String,
    private val authorities: Collection<GrantedAuthority>,
) : UserDetails {

    constructor(user: UserEntity) : this(
        userId = user.id,
        username = user.username,
        password = user.passwordHash,
        authorities = user.roles.flatMap { it.authorities() }.toSet()
    )

    override fun getUsername() = username
    override fun getPassword() = password
    override fun getAuthorities() = authorities
}