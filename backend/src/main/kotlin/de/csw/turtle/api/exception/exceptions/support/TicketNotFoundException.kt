package de.csw.turtle.api.exception.exceptions.support

import de.csw.turtle.api.exception.exceptions.TUrtleException
import org.springframework.http.HttpStatus

class TicketNotFoundException(id: Long) : TUrtleException(
    "Ticket '$id' not found.",
    HttpStatus.NOT_FOUND
)
