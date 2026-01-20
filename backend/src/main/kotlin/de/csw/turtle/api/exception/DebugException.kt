package de.csw.turtle.api.exception

class DebugException(message: String) : RuntimeException("This exception is intentional for debug purposes: $message")