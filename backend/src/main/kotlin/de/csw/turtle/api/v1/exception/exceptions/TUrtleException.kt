package de.csw.turtle.api.v1.exception.exceptions

import org.springframework.http.HttpStatus

open class TUrtleException(
    message: String,
    val status: HttpStatus
) : RuntimeException(message)