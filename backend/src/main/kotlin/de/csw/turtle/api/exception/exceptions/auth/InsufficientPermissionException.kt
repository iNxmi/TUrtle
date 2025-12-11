package de.csw.turtle.api.exception.exceptions.auth

import de.csw.turtle.api.Permission
import de.csw.turtle.api.exception.exceptions.TUrtleException
import org.springframework.http.HttpStatus

class InsufficientPermissionException(
    permission: Permission
) : TUrtleException(
    "You need '$permission' to access this resource.",
    HttpStatus.FORBIDDEN
)