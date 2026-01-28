package de.csw.turtle.api.filter

import de.csw.turtle.api.CustomUserDetails
import de.csw.turtle.api.dto.create.CreateAuditLogRequest
import de.csw.turtle.api.entity.AuditLogEntity
import de.csw.turtle.api.service.AuditLogService
import de.csw.turtle.api.service.UserService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class AuditLogFilter(
    private val auditLogService: AuditLogService,
    private val userService: UserService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        filterChain.doFilter(request, response)

        val principal = SecurityContextHolder.getContext().authentication?.principal
        if (principal !is CustomUserDetails)
            return

        val success = response.status in 200..<300
        if (!success)
            return

        val user = userService.getByUsername(principal.username)

        val createAuditLogRequest = CreateAuditLogRequest(
            userId = user.id,
            ipAddress = request.remoteAddr,
            endpoint = request.requestURI,
            httpMethod = AuditLogEntity.HttpMethod.valueOf(request.method.uppercase())
        )
        auditLogService.create(createAuditLogRequest)
    }

}