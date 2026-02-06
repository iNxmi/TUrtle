package de.csw.turtle.api.filter

import de.csw.turtle.api.CustomUserDetails
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.service.NetworkService
import de.csw.turtle.api.service.RateLimiterService
import de.csw.turtle.api.service.UserService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class RateLimitFilter(
    private val userService: UserService,
    private val rateLimiterService: RateLimiterService,
    private val networkService: NetworkService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val user = getUser()

        val ipAddress = networkService.getClientIp(request)
        if (!rateLimiterService.tryConsume(ipAddress, user)) {
            response.status = 429
            response.writer.write("Too many requests.")
            return
        }

        filterChain.doFilter(request, response)
    }

    fun getUser(): UserEntity? {
        val authentication = SecurityContextHolder.getContext().authentication
            ?: return null

        val principal = authentication.principal as? CustomUserDetails
            ?: return null

        val user = userService.getById(principal.userId)
            ?: TODO("implement exception")

        return user
    }

}