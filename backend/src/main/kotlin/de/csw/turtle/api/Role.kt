package de.csw.turtle.api

import org.springframework.security.core.authority.SimpleGrantedAuthority

//TODO convert to entity for custom roles

enum class Role(val permissions: Set<String>) {

    ANONYMOUS(setOf(
        "api.auth:login",
        "api.auth:register",

        "api.support:create"
    )),

    STUDENT(setOf(
        "api.profile:get",
        "api.profile:patch",
        "api.profile:delete",

        "api.auth:logout",

        "api.support:create"
    )),

    PROFESSOR(setOf(
        "api.profile:get",
        "api.profile:patch",
        "api.profile:delete",

        "api.auth:logout",

        "api.support:create"
    )),

    ADMINISTRATOR(setOf(
        "api.auth:login",
        "api.auth:register",
        "api.auth:logout",

        "api.profile:get",
        "api.profile:patch",
        "api.profile:delete"
    ));

    fun grantedAuthorities() = permissions.map { SimpleGrantedAuthority(it) }

}