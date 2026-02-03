package de.csw.turtle.api.filter

import de.csw.turtle.api.service.CustomUserDetailsService
import de.csw.turtle.api.service.JWTService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

private const val PREFIX = "Bearer "

@Component
class JWTAuthFilter(
    private val jwtService: JWTService,
    private val userDetailsService: CustomUserDetailsService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = request.cookies?.find { it.name == "access_token" }?.value
            ?: request.getHeader("Authorization")?.takeIf { it.startsWith(PREFIX) }?.substringAfter(PREFIX)

        if(token != null) {
            try {
                val username = jwtService.extract(token)
                if (SecurityContextHolder.getContext().authentication == null) {
                    val userDetails = userDetailsService.loadUserByUsername(username)
                    if (jwtService.isValid(token!!, userDetails)) {
                        val authToken = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                        authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                        SecurityContextHolder.getContext().authentication = authToken
                    }
                }
            } catch(_:Exception){
                // invalid token -> ignore, request remains unauthenticated
            }
        }

        filterChain.doFilter(request, response)
    }

}