package de.csw.turtle.api.configuration

import de.csw.turtle.api.DynamicPasswordEncoder
import de.csw.turtle.api.service.SystemSettingService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class GeneralConfiguration {

//    @Bean
//    fun passwordEncoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

    @Bean
    fun passwordEncoder(systemSettingService: SystemSettingService): PasswordEncoder =
        DynamicPasswordEncoder(systemSettingService)

    @Bean
    fun authenticationManager(authConfig: AuthenticationConfiguration): AuthenticationManager =
        authConfig.authenticationManager

}