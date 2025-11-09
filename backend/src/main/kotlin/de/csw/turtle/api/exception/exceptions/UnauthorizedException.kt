package de.csw.turtle.api.exception.exceptions

import org.springframework.http.HttpStatus

class UnauthorizedException() : TUrtleException(
    "Unauthorized.",
    HttpStatus.UNAUTHORIZED
)