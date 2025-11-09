package de.csw.turtle.api.exception.exceptions

import org.springframework.http.HttpStatus

open class TUrtleException(
    message: String,
    val status: HttpStatus
) : RuntimeException(message)