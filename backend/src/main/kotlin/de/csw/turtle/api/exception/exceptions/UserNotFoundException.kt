package de.csw.turtle.api.exception.exceptions

import org.springframework.http.HttpStatus

class UserNotFoundException(username: String) : TUrtleException(
    "User '$username' not found",
    HttpStatus.NOT_FOUND
)