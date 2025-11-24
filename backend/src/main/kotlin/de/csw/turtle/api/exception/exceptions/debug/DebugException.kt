package de.csw.turtle.api.exception.exceptions.debug

class DebugException(message: String?) : RuntimeException(
    "This exception is intentional for debug purposes: $message",
)