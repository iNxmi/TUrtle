package de.csw.turtle.api.exception

import org.springframework.http.HttpStatus

class InternalServerErrorException(message: String) : TUrtleException(message, HttpStatus.INTERNAL_SERVER_ERROR)