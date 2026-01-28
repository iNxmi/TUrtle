package de.csw.turtle.api.exception

import org.springframework.http.HttpStatus

class ForbiddenException(message: String = "") : TUrtleException(message, HttpStatus.FORBIDDEN)