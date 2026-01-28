package de.csw.turtle.api.service

import de.csw.turtle.api.SimpleUserDetails
import de.csw.turtle.api.exception.NotFoundException
import jakarta.transaction.Transactional
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userService: UserService
) : UserDetailsService {

    @Transactional
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userService.getByUsernameOrNull(username) ?: throw NotFoundException(username)

        return SimpleUserDetails(
            username = user.username,
            password = user.password,
            authorities = user.roles.flatMap { it.authorities() }.toSet()
        )
    }

}