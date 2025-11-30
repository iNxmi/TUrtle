package de.csw.turtle.api.service

import de.csw.turtle.api.SimpleUserDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val service: UserService
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = service.getOrNull(username)
            ?: throw UsernameNotFoundException(username)

        return SimpleUserDetails(
            username = user.username,
            password = user.password,
            authorities = user.roles.flatMap { it.authorities() }.toSet()
        )
    }

}