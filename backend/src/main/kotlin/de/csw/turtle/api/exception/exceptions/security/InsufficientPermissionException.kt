package de.csw.turtle.api.exception.exceptions.security

import de.csw.turtle.api.exception.exceptions.TUrtleException
import de.csw.turtle.api.security.Permission
import org.springframework.http.HttpStatus

class InsufficientPermissionException(permission: Permission) : TUrtleException(
    "You need the following permission to access this resource: $permission",
    HttpStatus.FORBIDDEN
)