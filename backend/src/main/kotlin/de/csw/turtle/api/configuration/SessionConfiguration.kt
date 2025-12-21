package de.csw.turtle.api.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.session.SessionRegistryImpl

@Configuration
class SessionConfiguration {

    @Bean
    fun sessionRegistry() = SessionRegistryImpl()

}