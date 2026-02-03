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
        val userId = if (principal is CustomUserDetails) {
            userService.getByUsername(principal.username).id
        } else null

        val createAuditLogRequest = CreateAuditLogRequest(
            userId = userId,
            ipAddress = request.remoteAddr,
            endpoint = request.requestURI,
            status = response.status,
            httpMethod = AuditLogEntity.HttpMethod.valueOf(request.method.uppercase())
        )
        auditLogService.create(createAuditLogRequest)
    }

}