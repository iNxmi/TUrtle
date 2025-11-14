package de.csw.turtle.api.exception.exceptions.exception

import de.csw.turtle.api.exception.exceptions.TUrtleException
import org.springframework.http.HttpStatus

class ExceptionNotFoundException(id: Long) : TUrtleException(
    "Exception '$id' not found.",
    HttpStatus.NOT_FOUND
)