package de.csw.turtle.api.exception.exceptions.device.category

import de.csw.turtle.api.exception.exceptions.TUrtleException
import org.springframework.http.HttpStatus

class DeviceCategoryNotFoundException(id: Long) : TUrtleException(
    "Device Category '$id' not found.",
    HttpStatus.NOT_FOUND
)