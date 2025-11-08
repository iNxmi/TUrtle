package de.csw.turtle.api.v1.exception.exceptions

import org.springframework.http.HttpStatus

class UnauthorizedException() : TUrtleException(
    "Unauthorized.",
    HttpStatus.UNAUTHORIZED
)