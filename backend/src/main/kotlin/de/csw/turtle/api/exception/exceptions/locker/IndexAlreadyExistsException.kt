package de.csw.turtle.api.exception.exceptions.locker

import de.csw.turtle.api.exception.exceptions.TUrtleException
import org.springframework.http.HttpStatus

class IndexAlreadyExistsException(index: Int) : TUrtleException(
    "Index '$index' already exists.",
    HttpStatus.CONFLICT
)