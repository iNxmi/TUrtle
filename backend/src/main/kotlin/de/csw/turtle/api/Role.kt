package de.csw.turtle.api

import org.springframework.security.core.authority.SimpleGrantedAuthority

//TODO convert to entity for custom roles

private val user = setOf(
    "api.profile:get",
    "api.profile:patch",
    "api.profile:delete",
    "api.auth:logout",
    "api.support:create"
)

enum class Role(val permissions: Set<String>) {

    ANONYMOUS(setOf(
        "api.auth:login",
        "api.auth:register",
        "api.support:create"
    )),

    STUDENT(user),
    PROFESSOR(user),

    ADMINISTRATOR(setOf("*:*"));

    fun getGrantedAuthorities() = permissions.map { SimpleGrantedAuthority(it) }

}