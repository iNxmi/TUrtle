package de.csw.turtle.api.exception.exceptions

import org.springframework.http.HttpStatus

class UsernameOrPasswordInvalidException : TUrtleException("Username or password invalid.", HttpStatus.UNAUTHORIZED)
