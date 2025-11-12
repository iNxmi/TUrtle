package de.csw.turtle.api.exception.exceptions.user

import de.csw.turtle.api.exception.exceptions.TUrtleException
import org.springframework.http.HttpStatus

class UsernameOrPasswordInvalidException : TUrtleException(
    "Username or password invalid.",
    HttpStatus.UNAUTHORIZED
)
