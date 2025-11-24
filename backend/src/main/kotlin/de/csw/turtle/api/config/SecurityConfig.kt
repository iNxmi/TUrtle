package de.csw.turtle.api.config

import de.csw.turtle.TUrtleProperties
import de.csw.turtle.api.service.CustomUserDetailsService
import de.csw.turtle.api.service.PasswordEncoderService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.session.SessionRegistry
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val properties: TUrtleProperties,
    private val sessionRegistry: SessionRegistry,
    private val passwordEncoderService: PasswordEncoderService,
    private val userDetailsService: CustomUserDetailsService,
    private val persistentTokenRepository: PersistentTokenRepository
) {

    @Bean
    fun authenticationManager(
        userDetailsService: UserDetailsService
    ): AuthenticationManager {
        val authenticationProvider = DaoAuthenticationProvider(userDetailsService)
        authenticationProvider.setPasswordEncoder(passwordEncoderService.encoder)

        return ProviderManager(authenticationProvider)
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }
            .cors { it.disable() }

            .sessionManagement {
                it.maximumSessions(properties.security.maxSessions)
                    .sessionRegistry(sessionRegistry)
                    .maxSessionsPreventsLogin(false)

                it.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            }

            .rememberMe {
                it.key(properties.security.sessionSecret)
                    .userDetailsService(userDetailsService)
                    .tokenRepository(persistentTokenRepository)
                    .tokenValiditySeconds(properties.security.rememberMeDuration.seconds.toInt())
                    .alwaysRemember(false)
            }

            .authorizeHttpRequests { it.anyRequest().permitAll() }
            .formLogin { it.disable() }
            .logout { it.disable() }
            .httpBasic { it.disable() }

        return http.build()
    }

}