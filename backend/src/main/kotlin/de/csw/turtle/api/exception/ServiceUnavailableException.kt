package de.csw.turtle.api.exception

import org.springframework.http.HttpStatus

class ServiceUnavailableException(message: String) : TUrtleException(message, HttpStatus.SERVICE_UNAVAILABLE)