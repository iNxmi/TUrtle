package de.csw.turtle.api.v1.service

import de.csw.turtle.api.v1.exception.exceptions.UserNotFoundException
import de.csw.turtle.api.v1.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val repository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String) = repository.findByUserName(username)
        ?: throw UserNotFoundException(username)
}