package de.csw.turtle.api.config

import de.csw.turtle.api.service.PasswordEncoderService
import org.springframework.beans.factory.annotation.Value
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

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val sessionRegistry: SessionRegistry,
    private val passwordEncoderService: PasswordEncoderService
) {

    @Value("\${turtle.api.max_sessions}")
    private val maxSession: Int = 16

    /**
     * Default value = 30 * seconds per day
     * so 30 day valid session token
     */
    @Value("\${turtle.api.session_duration_seconds}")
    private val sessionDurationInSeconds: Int = 30 * 24 * 60 * 60

    @Value("\${turtle.api.session_key}")
    private lateinit var sessionKey: String

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
                it.maximumSessions(maxSession)
                    .sessionRegistry(sessionRegistry)
                    .maxSessionsPreventsLogin(false)

                it.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            }

//            .rememberMe {
//                it.key(sessionKey)
//                    .userDetailsService(userDetailsService)
//                    .tokenRepository(persistentTokenRepository(dataSource))
//                    .tokenValiditySeconds(sessionDurationInSeconds)
//                    .alwaysRemember(false)
//            }

            .authorizeHttpRequests { it.anyRequest().permitAll() }
            .formLogin { it.disable() }
            .logout { it.disable() }
            .httpBasic { it.disable() }

        return http.build()
    }

}