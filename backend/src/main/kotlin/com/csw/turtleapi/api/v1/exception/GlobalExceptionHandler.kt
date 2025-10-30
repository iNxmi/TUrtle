package com.csw.turtleapi.api.v1.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(exception: Exception) = ResponseEntity.internalServerError().build<Any>()

}