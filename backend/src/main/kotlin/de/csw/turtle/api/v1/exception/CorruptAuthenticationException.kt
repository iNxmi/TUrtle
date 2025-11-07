package de.csw.turtle.api.v1.exception

import org.springframework.http.HttpStatus

class CorruptAuthenticationException(obj: Any) : TUrtleException(
    "Corrupt Authentication '$obj'.",
    HttpStatus.UNAUTHORIZED
)