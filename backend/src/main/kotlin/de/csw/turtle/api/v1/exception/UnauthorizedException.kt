package de.csw.turtle.api.v1.exception

import org.springframework.http.HttpStatus

class UnauthorizedException() : TUrtleException(
    "Unauthorized.",
    HttpStatus.UNAUTHORIZED
)