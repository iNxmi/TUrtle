package de.csw.turtle.api.filter

import de.csw.turtle.api.CustomUserDetails
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.service.CustomUserDetailsService
import de.csw.turtle.api.service.JWTService
import de.csw.turtle.api.service.UserService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.filter.OncePerRequestFilter

private const val AUTHORIZATION_HEADER = "Authorization"
private const val BEARER_PREFIX = "Bearer "
private const val COOKIE_NAME = "access_token"

@Component
class JWTAuthFilter(
    private val jwtService: JWTService,
    private val userService: UserService,
    private val customUserDetailsService: CustomUserDetailsService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            authenticate(request)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        filterChain.doFilter(request, response)
    }

    private fun authenticate(request: HttpServletRequest) {
        if (SecurityContextHolder.getContext().authentication != null)
            return

        val jwtToken = getJWTToken(request) ?: return
        if (jwtService.isExpired(jwtToken))
            return

        val data = jwtService.getData(jwtToken) ?: return
        if (data.type != JWTService.Type.ACCESS)
            return

        val user = userService.getById(data.subject) ?: return

        val details = customUserDetailsService.loadUserByUsername(user.username)
        val authenticationToken = UsernamePasswordAuthenticationToken(details, null, details.authorities)
        authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = authenticationToken
    }

    private fun getJWTToken(request: HttpServletRequest): String? {
        val cookieToken = request.cookies
            ?.find { it.name == COOKIE_NAME }
            ?.value

        val headerToken = request.getHeader(AUTHORIZATION_HEADER)
            ?.takeIf { it.startsWith(BEARER_PREFIX) }
            ?.substringAfter(BEARER_PREFIX)

        return cookieToken ?: headerToken
    }

}