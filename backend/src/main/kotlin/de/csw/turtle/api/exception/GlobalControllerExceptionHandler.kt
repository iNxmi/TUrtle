package de.csw.turtle.api.exception

import de.csw.turtle.api.dto.response.ExceptionResponse
import de.csw.turtle.api.exception.exceptions.TUrtleException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.data.mapping.PropertyReferenceException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.resource.NoResourceFoundException

@RestControllerAdvice
class GlobalControllerExceptionHandler {

    @ExceptionHandler(TUrtleException::class)
    fun turtle(exception: TUrtleException, request: HttpServletRequest) =
        exception.responseEntity(request.requestURI, exception.status)

    @ExceptionHandler(NoResourceFoundException::class)
    fun noResourceFound(exception: NoResourceFoundException, request: HttpServletRequest) =
        exception.responseEntity(request.requestURI, HttpStatus.NOT_FOUND)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun validation(exception: MethodArgumentNotValidException, request: HttpServletRequest) =
        exception.responseEntity(request.requestURI, HttpStatus.BAD_REQUEST)

    //TODO change name
    @ExceptionHandler(PropertyReferenceException::class)
    fun propertyReference(exception: PropertyReferenceException, request: HttpServletRequest) =
        exception.responseEntity(request.requestURI, HttpStatus.BAD_REQUEST)

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun httpRequestMethodNotSupported(exception: HttpRequestMethodNotSupportedException, request: HttpServletRequest) =
        exception.responseEntity(request.requestURI, HttpStatus.METHOD_NOT_ALLOWED)

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun methodArgumentTypeMismatch(exception: HttpRequestMethodNotSupportedException, request: HttpServletRequest) =
        exception.responseEntity(request.requestURI, HttpStatus.BAD_REQUEST)

    @ExceptionHandler(Exception::class)
    fun generic(exception: Exception, request: HttpServletRequest): ResponseEntity<ExceptionResponse> {
        exception.printStackTrace()
        return exception.responseEntity(request.requestURI, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    private fun Exception.responseEntity(path: String, status: HttpStatus): ResponseEntity<ExceptionResponse> {
        val response = ExceptionResponse(path, this, status)
        return ResponseEntity.status(status).body(response)
    }

}