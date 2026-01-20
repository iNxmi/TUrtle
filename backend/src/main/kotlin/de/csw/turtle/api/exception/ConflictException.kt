package de.csw.turtle.api.exception

import org.springframework.http.HttpStatus

class ConflictException(message: String) : TUrtleException(message, HttpStatus.CONFLICT)