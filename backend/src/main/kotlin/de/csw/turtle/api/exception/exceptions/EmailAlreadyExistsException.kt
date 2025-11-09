package de.csw.turtle.api.exception.exceptions

import org.springframework.http.HttpStatus

class EmailAlreadyExistsException(email: String) : TUrtleException(
    "Email '$email' already exists.",
    HttpStatus.CONFLICT
)