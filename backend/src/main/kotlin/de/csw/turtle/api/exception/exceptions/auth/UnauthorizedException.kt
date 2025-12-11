package de.csw.turtle.api.exception.exceptions.auth

import de.csw.turtle.api.exception.exceptions.TUrtleException
import org.springframework.http.HttpStatus

class UnauthorizedException() : TUrtleException(
    "You must be logged in to access this resource.",
    HttpStatus.UNAUTHORIZED
)