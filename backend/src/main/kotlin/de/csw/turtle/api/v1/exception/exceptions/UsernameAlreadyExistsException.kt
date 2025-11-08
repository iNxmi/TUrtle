package de.csw.turtle.api.v1.exception.exceptions

import org.springframework.http.HttpStatus

class UsernameAlreadyExistsException(username: String) : TUrtleException(
    "Username '$username' already exists.",
    HttpStatus.CONFLICT
)