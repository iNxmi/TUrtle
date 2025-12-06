package de.csw.turtle.api.exception.exceptions.crud

import de.csw.turtle.api.exception.exceptions.TUrtleException
import org.springframework.http.HttpStatus

class CRUDResourceNotFoundException(resource: String, id: Long) : TUrtleException(
    "CRUD Resource '$resource' with id '$id' not found.",
    HttpStatus.NOT_FOUND
)