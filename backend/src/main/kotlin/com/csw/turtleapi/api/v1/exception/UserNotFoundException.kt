package com.csw.turtleapi.api.v1.exception

import org.springframework.http.HttpStatus

class UserNotFoundException(username: String) : TUrtleException(
    "User '$username' not found",
    HttpStatus.NOT_FOUND
)