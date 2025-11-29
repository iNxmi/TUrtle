package de.csw.turtle.api.exception.exceptions.crud

import de.csw.turtle.api.exception.exceptions.TUrtleException
import org.springframework.http.HttpStatus

class CRUDResourceNotFoundException(id: Long) : TUrtleException(
    "CRUD Resource '$id' not found.",
    HttpStatus.NOT_FOUND
)