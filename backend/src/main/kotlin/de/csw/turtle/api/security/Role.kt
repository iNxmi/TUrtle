package de.csw.turtle.api.security

import de.csw.turtle.api.security.Permission.*
import org.springframework.security.core.GrantedAuthority

private val user = setOf(API_PROFILE_GET, API_PROFILE_PATCH, API_PROFILE_DELETE, API_AUTH_LOGOUT, API_SUPPORT_CREATE)

enum class Role(val permissions: Set<Permission>) {

    GUEST(setOf(API_AUTH_LOGIN, API_AUTH_REGISTER, API_SUPPORT_CREATE)),

    STUDENT(user),
    PROFESSOR(user),

    ADMINISTRATOR(Permission.entries.toSet());

    fun getGrantedAuthority() = GrantedAuthority { name }

}