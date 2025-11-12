package de.csw.turtle.api.exception.exceptions.user

import de.csw.turtle.api.exception.exceptions.TUrtleException
import org.springframework.http.HttpStatus

class UserNotFoundException(username: String) : TUrtleException(
    "User '$username' not found.",
    HttpStatus.NOT_FOUND
)