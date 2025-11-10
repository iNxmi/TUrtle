package de.csw.turtle.api.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import de.csw.turtle.api.dto.response.ExceptionResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class CustomAccessDeniedHandler : AccessDeniedHandler {

    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AccessDeniedException
    ) {
        val status = HttpStatus.FORBIDDEN
        val responseDto = ExceptionResponse(request.requestURI, exception, status)

        response.status = status.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer.write(ObjectMapper().writeValueAsString(responseDto))
    }

}
