package de.csw.turtle.api.v1.exception.exceptions

import org.springframework.http.HttpStatus

class StudentIdAlreadyExistsException(id: Long) : TUrtleException(
    "Student ID '$id' already exists.",
    HttpStatus.CONFLICT
)