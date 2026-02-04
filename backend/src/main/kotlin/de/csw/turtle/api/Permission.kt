package de.csw.turtle.api

/*
 * The permission names follow a simple format:
 *
 * Scenario: We want to create a permission for a user login
 *
 * ENVIRONMENT = FRONTEND / BACKEND
 * ENDPOINT = The corresponding endpoint, defined by the controller (/api/auth), separated by an underscore (_) except slashes (/), e.g. /api/auth/login -> API_AUTH
 * ACTION = A simple string to identify what the permission does, e.g. when logging in -> LOGIN
 *
 * {ENVIRONMENT}__{ENDPOINT}__{ACTION} -> BACKEND__API_AUTH__LOGIN
 */

enum class Permission {
    MANAGE_AUDIT_LOGS,
    MANAGE_DOOR,
    MANAGE_DEVICE_CATEGORIES,
    MANAGE_DEVICES,
    MANAGE_EXCEPTIONS,
    MANAGE_LOCKERS,
    MANAGE_SUPPORT_TICKETS,
    MANAGE_ROLES,
    MANAGE_USERS,
    MANAGE_EMAIL_TEMPLATES,
    MANAGE_GENERAL_TEMPLATES,
    MANAGE_FAQ,
    MANAGE_ROOM_BOOKINGS,
    MANAGE_DEVICE_BOOKINGS,
    MANAGE_SYSTEM_SETTINGS
}
