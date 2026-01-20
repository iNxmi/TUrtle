package de.csw.turtle.api.exception

import org.springframework.http.HttpStatus

class BadCredentialsException(message: String) : TUrtleException(message, HttpStatus.BAD_REQUEST)