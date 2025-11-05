package de.csw.turtle.api.v1.filter

import de.csw.turtle.api.v1.repository.UserRepository
import de.csw.turtle.api.v1.service.JwtService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthFilter(
    private val userRepository: UserRepository,
    private val jwtService: JwtService,
) : OncePerRequestFilter() {

    private val prefix = "Bearer "

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val header = request.getHeader("Authorization") ?: run {
            filterChain.doFilter(request, response)
            return
        }

        if (!header.startsWith(prefix)) {
            filterChain.doFilter(request, response)
            return
        }

        val token = header.substring(prefix.length)
        val username = jwtService.extract(token)

        val user = userRepository.findByUsername(username) ?: run {
            filterChain.doFilter(request, response)
            return
        }

        val context = SecurityContextHolder.getContext()
        if (context.authentication != null) {
            filterChain.doFilter(request, response)
            return
        }

        val auth = UsernamePasswordAuthenticationToken(user.username, null, emptyList())
        context.authentication = auth

        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val path = request.servletPath
        return path.startsWith("/api/v1/auth/") ||
                path.startsWith("/openapi/api-docs/") ||
                path.startsWith("/swagger") ||
                path.startsWith("/docs")
    }

}