package de.csw.turtle.api.exception

import de.csw.turtle.api.dto.ExceptionResponse
import de.csw.turtle.api.dto.create.CreateExceptionRequest
import de.csw.turtle.api.service.ExceptionService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.data.mapping.PropertyReferenceException
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
    private val exceptionService: ExceptionService,
) {

    @ExceptionHandler(HttpException::class)
    fun http(exception: HttpException, request: HttpServletRequest) = getResponse(exception, request)

    @ExceptionHandler(NoResourceFoundException::class)
    fun notFound(
        exception: NoResourceFoundException,
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
        MethodArgumentNotValidException::class
    )
    fun badRequest(
        exception: MissingServletRequestParameterException,
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

        val createExceptionRequest = CreateExceptionRequest(
            endpoint = url,
            exception = exception::class.simpleName!!,
            message = exception.message,
            stackTrace = getStackTraceAsString(exception)
        )
        val entity = exceptionService.create(createExceptionRequest)

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