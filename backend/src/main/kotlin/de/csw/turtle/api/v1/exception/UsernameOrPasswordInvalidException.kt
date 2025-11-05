package de.csw.turtle.api.v1.exception

import org.springframework.http.HttpStatus

class UsernameOrPasswordInvalidException : TUrtleException("Username or password invalid.", HttpStatus.UNAUTHORIZED)
