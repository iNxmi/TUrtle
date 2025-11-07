package de.csw.turtle.api.v1.config.security

import de.csw.turtle.api.v1.service.TUrtleUserDetailsService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository
import javax.sql.DataSource

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val authenticationEntryPoint: CustomAuthenticationEntryPoint,
    private val accessDeniedHandler: CustomAccessDeniedHandler,
    private val dataSource: DataSource,
    private val userDetailsService: TUrtleUserDetailsService
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
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun persistentTokenRepository(dataSource: DataSource): PersistentTokenRepository {
        val repository = JdbcTokenRepositoryImpl()
        repository.setDataSource(dataSource)
        return repository
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }

            .sessionManagement {
                it.maximumSessions(maxSession)
                    .maxSessionsPreventsLogin(false)
            }

            .rememberMe {
                it.key(sessionKey)
                    .userDetailsService(userDetailsService)
                    .tokenRepository(persistentTokenRepository(dataSource))
                    .tokenValiditySeconds(sessionDurationInSeconds)
                    .alwaysRemember(false)
            }

            .authorizeHttpRequests {
//                it.requestMatchers(
//                    "/docs",
//                    "/swagger-ui/**",
//                    "/swagger-ui.html",
//                    "/openapi/api-docs/**"
//                ).permitAll()
//
//                    .requestMatchers("/api/v1/auth/**").permitAll()
//
//                    .anyRequest().authenticated()

                it.anyRequest().permitAll()
            }

            .formLogin { it.loginProcessingUrl("/api/v1/auth/login").permitAll() }
            .logout { it.logoutUrl("/api/v1/auth/logout").logoutSuccessUrl("/").permitAll() }
            .httpBasic { it.disable() }

            .exceptionHandling {
                it.authenticationEntryPoint(authenticationEntryPoint)
                    .accessDeniedHandler(accessDeniedHandler)
            }

        return http.build()
    }

}