package de.csw.turtle.api.config

import de.csw.turtle.TUrtleProperties
import de.csw.turtle.api.service.CustomUserDetailsService
import de.csw.turtle.api.service.OidcRegistrationService
import de.csw.turtle.api.service.UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.session.SessionRegistry
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.filter.ForwardedHeaderFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfiguration(
    private val properties: TUrtleProperties,
    private val sessionRegistry: SessionRegistry,
    private val oidcService: OidcRegistrationService
) {

    @Bean
    fun forwardedHeaderFilter() = ForwardedHeaderFilter()

    @Bean
    fun authenticationManager(authConfig: AuthenticationConfiguration): AuthenticationManager =
        authConfig.authenticationManager

    @Bean
    fun passwordEncoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

    @Bean
    fun filterChain(http: HttpSecurity, userDetailsService: CustomUserDetailsService): SecurityFilterChain {
        http.csrf { it.disable() }
            .cors { it.disable() }

            .oauth2Login { oauth ->
                oauth.userInfoEndpoint { u ->
                    u.oidcUserService(oidcService)
                }
            }
            .sessionManagement {
                it.maximumSessions(properties.security.maxSessions)
                    .sessionRegistry(sessionRegistry)
                    .maxSessionsPreventsLogin(false)

                it.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            }

            .anonymous {}
            .authorizeHttpRequests { it.anyRequest().permitAll() }
            .userDetailsService(userDetailsService)

            .formLogin { it.disable() }
            .logout { it.disable() }
            .httpBasic { it.disable() }

        return http.build()
    }

}