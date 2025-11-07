package de.csw.turtle.api.v1.service

import de.csw.turtle.api.v1.exception.UserNotFoundException
import de.csw.turtle.api.v1.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class TUrtleUserDetailsService(
    private val repository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = repository.findByUserName(username)
            ?: throw UserNotFoundException(username)

        return user
    }
}