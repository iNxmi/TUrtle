package de.csw.turtle.api.exception.exceptions.user

import de.csw.turtle.api.exception.exceptions.TUrtleException
import org.springframework.http.HttpStatus

class StudentIdAlreadyExistsException(id: Long) : TUrtleException(
    "Student ID '$id' already exists.",
    HttpStatus.CONFLICT
)