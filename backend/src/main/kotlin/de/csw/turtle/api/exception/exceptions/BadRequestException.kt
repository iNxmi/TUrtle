package de.csw.turtle.api.exception.exceptions

import org.springframework.http.HttpStatus

class BadRequestException(message: String) : TUrtleException(message, HttpStatus.BAD_REQUEST)