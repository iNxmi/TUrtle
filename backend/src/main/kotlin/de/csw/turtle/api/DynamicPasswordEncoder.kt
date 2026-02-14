package de.csw.turtle.api

import de.csw.turtle.api.service.SystemSettingService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

class DynamicPasswordEncoder(
    private val systemSettingService: SystemSettingService
): PasswordEncoder {

    override fun encode(rawPassword: CharSequence): String? {
        val strength = systemSettingService.getTyped<Int>(Settings.SECURITY_BCRYPT_STRENGTH)
        val bCrypt = BCryptPasswordEncoder(strength)
        return bCrypt.encode(rawPassword)
    }

    private val matcher = BCryptPasswordEncoder()
    override fun matches(rawPassword: CharSequence, encodedPassword: String) = matcher.matches(rawPassword, encodedPassword)

}