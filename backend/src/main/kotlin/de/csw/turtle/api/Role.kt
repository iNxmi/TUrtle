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
        "debug:info",
        "debug:door",
        "debug:locker",
        "debug:exception",
        "debug:email",

        "api.auth:login",
        "api.auth:register",
        "api.auth:logout",

        "api.profile:get",
        "api.profile:patch",
        "api.profile:delete",

        "api.auditlogs:create",
        "api.auditlogs:get",
        "api.auditlogs:patch",
        "api.auditlogs:delete",

        "api.devicecategories:create",
        "api.devicecategories:get",
        "api.devicecategories:patch",
        "api.devicecategories:delete",

        "api.devices:create",
        "api.devices:get",
        "api.devices:patch",
        "api.devices:delete",

        "api.exceptions:create",
        "api.exceptions:get",
        "api.exceptions:patch",
        "api.exceptions:delete",

        "api.lockers:create",
        "api.lockers:get",
        "api.lockers:patch",
        "api.lockers:delete",

        "api.support:create",
        "api.support:get",
        "api.support:patch",
        "api.support:delete",

        "api.users:create",
        "api.users:get",
        "api.users:patch",
        "api.users:delete",
    ));

    fun grantedAuthorities() = permissions.map { SimpleGrantedAuthority(it) }

}