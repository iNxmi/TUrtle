package de.csw.turtle.api.exception

import org.springframework.http.HttpStatus

class UnauthorizedException(message: String) : TUrtleException(message, HttpStatus.UNAUTHORIZED)