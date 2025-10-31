package com.csw.turtleapi.api.v1.exception

class UserNotFoundException(username: String) : RuntimeException("User '$username' not found")