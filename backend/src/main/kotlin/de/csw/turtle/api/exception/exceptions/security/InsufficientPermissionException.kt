package de.csw.turtle.api.exception.exceptions.security

import de.csw.turtle.api.Permission
import de.csw.turtle.api.exception.exceptions.TUrtleException
import org.springframework.http.HttpStatus

class InsufficientPermissionException(
    permission: Permission
) : TUrtleException(
    "You need '$permission' to access this resource.",
    HttpStatus.FORBIDDEN
)