package de.csw.turtle.api.exception

import org.springframework.http.HttpStatus

abstract class TUrtleException(message: String, val status: HttpStatus) : RuntimeException(message)