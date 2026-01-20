package de.csw.turtle.api.exception

import org.springframework.http.HttpStatus

class BadRequestException(message: String) : TUrtleException(message, HttpStatus.BAD_REQUEST)