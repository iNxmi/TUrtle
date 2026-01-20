package de.csw.turtle.api.exception

import org.springframework.http.HttpStatus

class NotFoundException(message: String) : TUrtleException(message, HttpStatus.NOT_FOUND)