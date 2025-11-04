package de.csw.turtle.api.v1.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import de.csw.turtle.api.v1.dto.response.ExceptionResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationEntryPoint: AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException
    ) {
        val status = HttpStatus.FORBIDDEN
        val responseDto = ExceptionResponse(request.requestURI, exception, status)

        response.status = status.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer.write(ObjectMapper().writeValueAsString(responseDto))
    }

}
