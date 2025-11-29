package de.csw.turtle.api

enum class Permission(val path: String, val action: String) {

    DEBUG__DOOR("debug", "door"),
    DEBUG__LOCKER("debug", "locker"),
    DEBUG__EXCEPTION("debug", "exception"),
    DEBUG__EMAIL("debug", "email"),
    DEBUG__INFO("debug", "info"),

    API_AUTH__LOGIN("api.auth", "login"),
    API_AUTH__LOGOUT("api.auth", "logout"),
    API_AUTH__REGISTER("api.auth", "register"),

    API_PROFILE__GET("api.profile", "get"),
    API_PROFILE__PATCH("api.profile", "patch"),
    API_PROFILE__DELETE("api.profile", "delete"),

    API_RESOURCES_AUDITLOGS__CREATE("api.resources.auditlogs", "create"),
    API_RESOURCES_AUDITLOGS__GET("api.resources.auditlogs", "get"),
    API_RESOURCES_AUDITLOGS__PATCH("api.resources.auditlogs", "patch"),
    API_RESOURCES_AUDITLOGS__DELETE("api.resources.auditlogs", "delete"),

    API_RESOURCES_DEVICECATEGORIES__CREATE("api.resources.devicecategories", "create"),
    API_RESOURCES_DEVICECATEGORIES__GET("api.resources.devicecategories", "get"),
    API_RESOURCES_DEVICECATEGORIES__PATCH("api.resources.devicecategories", "patch"),
    API_RESOURCES_DEVICECATEGORIES__DELETE("api.resources.devicecategories", "delete"),

    API_RESOURCES_DEVICES__CREATE("api.resources.devices", "create"),
    API_RESOURCES_DEVICES__GET("api.resources.devices", "get"),
    API_RESOURCES_DEVICES__PATCH("api.resources.devices", "patch"),
    API_RESOURCES_DEVICES__DELETE("api.resources.devices", "delete"),

    API_RESOURCES_EXCEPTIONS__CREATE("api.resources.exceptions", "create"),
    API_RESOURCES_EXCEPTIONS__GET("api.resources.exceptions", "get"),
    API_RESOURCES_EXCEPTIONS__PATCH("api.resources.exceptions", "patch"),
    API_RESOURCES_EXCEPTIONS__DELETE("api.resources.exceptions", "delete"),

    API_RESOURCES_LOCKERS__CREATE("api.resources.lockers", "create"),
    API_RESOURCES_LOCKERS__GET("api.resources.lockers", "get"),
    API_RESOURCES_LOCKERS__PATCH("api.resources.lockers", "patch"),
    API_RESOURCES_LOCKERS__DELETE("api.resources.lockers", "delete"),

    API_RESOURCES_SUPPORT__CREATE("api.resources.support", "create"),
    API_RESOURCES_SUPPORT__GET("api.resources.support", "get"),
    API_RESOURCES_SUPPORT__PATCH("api.resources.support", "patch"),
    API_RESOURCES_SUPPORT__DELETE("api.resources.support", "delete"),

    API_RESOURCES_USERS__CREATE("api.resources.users", "create"),
    API_RESOURCES_USERS__GET("api.resources.users", "get"),
    API_RESOURCES_USERS__PATCH("api.resources.users", "patch"),
    API_RESOURCES_USERS__DELETE("api.resources.users", "delete"),

    API_RESOURCES_ROLES__CREATE("api.resources.roles", "create"),
    API_RESOURCES_ROLES__GET("api.resources.roles", "get"),
    API_RESOURCES_ROLES__PATCH("api.resources.roles", "patch"),
    API_RESOURCES_ROLES__DELETE("api.resources.roles", "delete"),

    TEMPORARY("", "");

    val permission = "$path:$action"

}
