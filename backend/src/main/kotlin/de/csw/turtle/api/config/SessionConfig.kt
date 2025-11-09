package de.csw.turtle.api.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.session.SessionRegistryImpl

@Configuration
class SessionConfig {

    @Bean
    fun sessionRegistry() = SessionRegistryImpl()

}