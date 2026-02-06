package de.csw.turtle.api.filter

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
import org.springframework.web.filter.OncePerRequestFilter

private const val BEARER_PREFIX = "Bearer "
private const val COOKIE_NAME = "access_token"

@Component
class JWTAuthFilter(
    private val jwtService: JWTService,
    private val userDetailsService: CustomUserDetailsService,
    private val userService: UserService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val cookieToken = request.cookies
            ?.find { it.name == COOKIE_NAME }
            ?.value

        val headerToken = request.getHeader("Authorization")
            ?.takeIf { it.startsWith(BEARER_PREFIX) }
            ?.substringAfter(BEARER_PREFIX)

        val token = cookieToken ?: headerToken

        if (token != null) {
            try {
                val data = jwtService.getData(token)
                if (data.type == JWTService.Type.ACCESS) {
                    val user = userService.getById(data.subject)
                        ?: TODO("implement exception")

                    val userDetails = userDetailsService.loadUserByUsername(user.username)
                    if (!jwtService.isExpired(token)) {
                        val authToken = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                        authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                        SecurityContextHolder.getContext().authentication = authToken
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        filterChain.doFilter(request, response)
    }

}