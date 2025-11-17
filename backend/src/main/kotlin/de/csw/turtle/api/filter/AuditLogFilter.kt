package de.csw.turtle.api.filter

import de.csw.turtle.api.entity.AuditLogEntity
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.repository.AuditLogRepository
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class AuditLogFilter(
    private val auditLogRepository: AuditLogRepository,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        filterChain.doFilter(request, response)

        val principal = SecurityContextHolder.getContext().authentication?.principal
        if (principal !is UserEntity)
            return

        val success = response.status in 200..<300
        if (!success)
            return

        val entity = AuditLogEntity(
            user = principal,
            ipAddress = request.remoteAddr,
            endpoint = request.requestURI,
            httpMethod = AuditLogEntity.HttpMethod.valueOf(request.method.uppercase())
        )
        auditLogRepository.save(entity)
    }

}