package de.csw.turtle.api.exception

import de.csw.turtle.api.dto.ExceptionResponse
import de.csw.turtle.api.dto.create.CreateExceptionRequest
import de.csw.turtle.api.service.ExceptionService
import jakarta.servlet.http.HttpServletRequest
import org.apache.coyote.BadRequestException
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

    @ExceptionHandler(TUrtleException::class)
    fun turtle(exception: TUrtleException, request: HttpServletRequest): ResponseEntity<ExceptionResponse> {
        val response = ExceptionResponse(request.requestURI, exception, exception.status)
        return ResponseEntity.status(exception.status).body(response)
    }

    @ExceptionHandler(NoResourceFoundException::class)
    fun noResourceFound(exception: NoResourceFoundException): Nothing =
        throw NotFoundException(exception.resourcePath)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun validation(exception: MethodArgumentNotValidException): Nothing =
        throw BadRequestException(exception.message)

    @ExceptionHandler(PropertyReferenceException::class)
    fun propertyReference(exception: PropertyReferenceException): Nothing =
        throw BadRequestException(exception.message)

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun httpRequestMethodNotSupported(exception: HttpRequestMethodNotSupportedException): Nothing =
        throw BadRequestException(exception.message)

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun methodArgumentTypeMismatch(exception: MethodArgumentTypeMismatchException): Nothing =
        throw BadRequestException(exception.message)

    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun missingServletRequestParameter(exception: MissingServletRequestParameterException): Nothing =
        throw BadRequestException(exception.message)

    @ExceptionHandler(Exception::class)
    fun exception(exception: Exception, request: HttpServletRequest): ResponseEntity<ExceptionResponse> {
        val url = request.requestURI

        val request = CreateExceptionRequest(
            endpoint = url,
            exception = exception::class.simpleName!!,
            message = exception.message,
            stackTrace = getStackTraceAsString(exception)
        )
        val entity = exceptionService.create(request)

        exception.printStackTrace()

        throw InternalServerErrorException("id=${entity.id}")
    }

    private fun getStackTraceAsString(exception: Throwable): String {
        val stringWriter = StringWriter()
        val printWriter = PrintWriter(stringWriter)
        exception.printStackTrace(printWriter)
        return stringWriter.toString()
    }

}