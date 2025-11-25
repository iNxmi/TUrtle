package de.csw.turtle.api.exception.exceptions.device

import de.csw.turtle.api.exception.exceptions.TUrtleException
import org.springframework.http.HttpStatus

class DeviceNotFoundException(id: Long) : TUrtleException(
    "Device '$id' not found.",
    HttpStatus.NOT_FOUND
)