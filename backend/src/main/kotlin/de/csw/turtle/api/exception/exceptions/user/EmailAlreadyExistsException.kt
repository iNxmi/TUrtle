package de.csw.turtle.api.exception.exceptions.user

import de.csw.turtle.api.exception.exceptions.TUrtleException
import org.springframework.http.HttpStatus

class EmailAlreadyExistsException(email: String) : TUrtleException(
    "Email '$email' already exists.",
    HttpStatus.CONFLICT
)