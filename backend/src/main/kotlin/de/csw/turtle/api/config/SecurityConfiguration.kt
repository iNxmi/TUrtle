package de.csw.turtle.api.config

import de.csw.turtle.TUrtleProperties
import de.csw.turtle.api.service.PasswordEncoderService
import de.csw.turtle.api.service.RoleService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.session.SessionRegistry
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfiguration(
    private val properties: TUrtleProperties,
    private val sessionRegistry: SessionRegistry,
    private val passwordEncoderService: PasswordEncoderService,
    private val roleService: RoleService,

//    private val userDetailsService: CustomUserDetailsService,
//    private val persistentTokenRepository: PersistentTokenRepository
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

        val anonymous = mutableListOf<GrantedAuthority>()
        val role = roleService.getByName("Anonymous")
        if(role != null)
            anonymous.addAll(role.grantedAuthorities())

        http.csrf { it.disable() }
            .cors { it.disable() }

            .sessionManagement {
                it.maximumSessions(properties.security.maxSessions)
                    .sessionRegistry(sessionRegistry)
                    .maxSessionsPreventsLogin(false)

                it.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            }

//            .rememberMe {
//                it.key(properties.security.sessionSecret)
//                    .userDetailsService(userDetailsService)
//                    .tokenRepository(persistentTokenRepository)
//                    .tokenValiditySeconds(properties.security.rememberMeDuration.seconds.toInt())
//                    .alwaysRemember(false)
//            }

            .authorizeHttpRequests {
                it.requestMatchers("/api/*").authenticated()
                it.anyRequest().permitAll()
            }

            .anonymous {
                it.authorities(anonymous)
            }

            .formLogin { it.disable() }
            .logout { it.disable() }
            .httpBasic { it.disable() }

        return http.build()
    }

}