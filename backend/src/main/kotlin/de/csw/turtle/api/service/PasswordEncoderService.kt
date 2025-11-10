package de.csw.turtle.api.service

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class PasswordEncoderService {

    val encoder = BCryptPasswordEncoder()

    fun encode(raw: String) = encoder.encode(raw)
    fun matches(raw: String, hash: String) = encoder.matches(raw, hash)

}