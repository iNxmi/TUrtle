package de.csw.turtle.api.service

import de.csw.turtle.api.exception.exceptions.UserNotFoundException
import de.csw.turtle.api.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val repository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String) = repository.findByUserName(username)
        ?: throw UserNotFoundException(username)
}