package de.csw.turtle.api.v1.exception

import org.springframework.http.HttpStatus

class EmailAlreadyExistsException(email: String) : TUrtleException(
    "Email '$email' already exists.",
    HttpStatus.CONFLICT
)