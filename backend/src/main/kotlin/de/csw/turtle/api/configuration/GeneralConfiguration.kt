package de.csw.turtle.api.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import de.csw.turtle.api.DynamicPasswordEncoder
import de.csw.turtle.api.service.ConfigurationService
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
    fun passwordEncoder(configurationService: ConfigurationService): PasswordEncoder =
        DynamicPasswordEncoder(configurationService)

    @Bean
    fun objectMapper(): ObjectMapper = ObjectMapper()

    @Bean
    fun authenticationManager(authConfig: AuthenticationConfiguration): AuthenticationManager =
        authConfig.authenticationManager

}