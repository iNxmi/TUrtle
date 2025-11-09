package de.csw.turtle.api.exception.exceptions

import org.springframework.http.HttpStatus

class UsernameAlreadyExistsException(username: String) : TUrtleException(
    "Username '$username' already exists.",
    HttpStatus.CONFLICT
)