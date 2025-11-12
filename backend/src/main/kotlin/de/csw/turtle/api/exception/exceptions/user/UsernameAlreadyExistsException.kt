package de.csw.turtle.api.exception.exceptions.user

import de.csw.turtle.api.exception.exceptions.TUrtleException
import org.springframework.http.HttpStatus

class UsernameAlreadyExistsException(username: String) : TUrtleException(
    "Username '$username' already exists.",
    HttpStatus.CONFLICT
)