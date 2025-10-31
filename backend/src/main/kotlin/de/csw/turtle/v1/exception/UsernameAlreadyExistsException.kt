package de.csw.turtle.v1.exception

import org.springframework.http.HttpStatus

class UsernameAlreadyExistsException(username: String) : TUrtleException(
    "Username '$username' already exists",
    HttpStatus.CONFLICT
)