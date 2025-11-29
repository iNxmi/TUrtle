package de.csw.turtle.api.exception

import de.csw.turtle.api.dto.ExceptionResponse
import de.csw.turtle.api.entity.ExceptionEntity
import de.csw.turtle.api.exception.exceptions.TUrtleException
import de.csw.turtle.api.exception.exceptions.debug.DebugException
import de.csw.turtle.api.repository.ExceptionRepository
import jakarta.servlet.http.HttpServletRequest
import org.springframework.data.mapping.PropertyReferenceException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.resource.NoResourceFoundException
import java.io.PrintWriter
import java.io.StringWriter

@RestControllerAdvice
class GlobalControllerExceptionHandler(
    private val exceptionRepository: ExceptionRepository,
) {

    @ExceptionHandler(TUrtleException::class)
    fun turtle(exception: TUrtleException, request: HttpServletRequest) =
        exception.responseEntity(request.requestURI, exception.status)

    @ExceptionHandler(NoResourceFoundException::class)
    fun noResourceFound(exception: NoResourceFoundException, request: HttpServletRequest) =
        exception.responseEntity(request.requestURI, HttpStatus.NOT_FOUND)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun validation(exception: MethodArgumentNotValidException, request: HttpServletRequest) =
        exception.responseEntity(request.requestURI, HttpStatus.BAD_REQUEST)

    @ExceptionHandler(PropertyReferenceException::class)
    fun propertyReference(exception: PropertyReferenceException, request: HttpServletRequest) =
        exception.responseEntity(request.requestURI, HttpStatus.BAD_REQUEST)

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun httpRequestMethodNotSupported(exception: HttpRequestMethodNotSupportedException, request: HttpServletRequest) =
        exception.responseEntity(request.requestURI, HttpStatus.METHOD_NOT_ALLOWED)

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun methodArgumentTypeMismatch(exception: MethodArgumentTypeMismatchException, request: HttpServletRequest) =
        exception.responseEntity(request.requestURI, HttpStatus.BAD_REQUEST)

    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun missingServletRequestParameter(
        exception: MissingServletRequestParameterException,
        request: HttpServletRequest
    ) =
        exception.responseEntity(request.requestURI, HttpStatus.BAD_REQUEST)

    @ExceptionHandler(Exception::class)
    fun generic(exception: Exception, request: HttpServletRequest): ResponseEntity<ExceptionResponse> {
        val url = request.requestURI

        val exceptionEntity = ExceptionEntity(
            endpoint = url,
            exception = exception::class.simpleName,
            message = exception.message,
            stackTrace = getStackTraceAsString(exception)
        )
        exceptionRepository.save(exceptionEntity)

        if (exception !is DebugException)
            exception.printStackTrace()

        return exception.responseEntity(request.requestURI, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    private fun getStackTraceAsString(exception: Throwable): String {
        val stringWriter = StringWriter()
        val printWriter = PrintWriter(stringWriter)
        exception.printStackTrace(printWriter)
        return stringWriter.toString()
    }

    private fun Exception.responseEntity(path: String, status: HttpStatus): ResponseEntity<ExceptionResponse> {
        val response = ExceptionResponse(path, this, status)
        return ResponseEntity.status(status).body(response)
    }

}