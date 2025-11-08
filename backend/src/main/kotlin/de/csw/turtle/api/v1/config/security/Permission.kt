package de.csw.turtle.api.v1.config.security

enum class Permission {
    ALL,

    USERS_CREATE,
    USERS_GET_PAGINATED,
    USERS_GET_BY_USERNAME,
    USERS_DELETE_BY_USERNAME
}