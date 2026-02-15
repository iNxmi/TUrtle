package de.csw.turtle.api.exception

import de.csw.turtle.api.dto.exception.ExceptionResponse
import de.csw.turtle.api.service.ExceptionService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.data.core.PropertyReferenceException
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
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
class ControllerExceptionHandler(
    private val exceptionService: ExceptionService,
) {

    @ExceptionHandler(HttpException::class)
    fun http(exception: HttpException, request: HttpServletRequest) = getResponse(exception, request)

    @ExceptionHandler(NoResourceFoundException::class)
    fun notFound(
        exception: Exception,
        request: HttpServletRequest
    ): ResponseEntity<ExceptionResponse> {
        val httpException = HttpException.NotFound(exception.message)
        return getResponse(httpException, request)
    }

    @ExceptionHandler(
        MissingServletRequestParameterException::class,
        MethodArgumentTypeMismatchException::class,
        HttpRequestMethodNotSupportedException::class,
        PropertyReferenceException::class,
        MethodArgumentNotValidException::class,
        HttpMessageNotReadableException::class
    )
    fun badRequest(
        exception: Exception,
        request: HttpServletRequest
    ): ResponseEntity<ExceptionResponse> {
        val httpException = HttpException.BadRequest(exception.message)
        return getResponse(httpException, request)
    }

    @ExceptionHandler(Exception::class)
    fun exception(
        exception: Exception,
        request: HttpServletRequest
    ): ResponseEntity<ExceptionResponse> {
        val url = request.requestURI

        val entity = exceptionService.create(
            endpoint = url,
            exception = exception::class.simpleName!!,
            message = exception.message,
            stackTrace = getStackTraceAsString(exception)
        )

        exception.printStackTrace()

        val message =
            "If you are not a system administrator please report this. This exception has been logged with id '${entity.id}'."
        val httpException = HttpException.InternalServerError(message)
        return getResponse(httpException, request)
    }

    private fun getStackTraceAsString(exception: Throwable): String {
        val stringWriter = StringWriter()
        val printWriter = PrintWriter(stringWriter)
        exception.printStackTrace(printWriter)
        return stringWriter.toString()
    }

    private fun getResponse(
        exception: HttpException,
        request: HttpServletRequest
    ): ResponseEntity<ExceptionResponse> {
        val response = ExceptionResponse(request.requestURI, exception.message!!, exception.status)
        return ResponseEntity.status(exception.status).body(response)
    }

}