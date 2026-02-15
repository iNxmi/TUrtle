package de.csw.turtle.api

import de.csw.turtle.api.entity.ConfigurationEntity.Key
import de.csw.turtle.api.service.ConfigurationService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

class DynamicPasswordEncoder(
    private val configurationService: ConfigurationService
) : PasswordEncoder {

    override fun encode(rawPassword: CharSequence?): String? {
        val strength = configurationService.getTyped<Int>(Key.SECURITY_BCRYPT_STRENGTH)
        val bCrypt = BCryptPasswordEncoder(strength)
        return bCrypt.encode(rawPassword)
    }

    private val matcher = BCryptPasswordEncoder()
    override fun matches(rawPassword: CharSequence?, encodedPassword: String?) =
        matcher.matches(rawPassword, encodedPassword)

}