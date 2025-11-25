package de.csw.turtle.api.exception.exceptions.locker

import de.csw.turtle.api.exception.exceptions.TUrtleException
import org.springframework.http.HttpStatus

class LockerNotFoundException(id: Long) : TUrtleException(
    "Locker '$id' not found.",
    HttpStatus.NOT_FOUND
)