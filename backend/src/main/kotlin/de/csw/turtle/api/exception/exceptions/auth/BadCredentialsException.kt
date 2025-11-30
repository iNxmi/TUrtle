package de.csw.turtle.api.exception.exceptions.auth

import de.csw.turtle.api.exception.exceptions.TUrtleException
import org.springframework.http.HttpStatus

class BadCredentialsException : TUrtleException(
    "Bad credentials.",
    HttpStatus.BAD_REQUEST
)