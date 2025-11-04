package de.csw.turtle.api.v1.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val authenticationEntryPoint: CustomAuthenticationEntryPoint,
    private val accessDeniedHandler: CustomAccessDeniedHandler
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }

            .authorizeHttpRequests {
                it.requestMatchers(
                    "/docs",
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/openapi/api-docs/**"
                ).permitAll()

                    .requestMatchers("/api/v1/auth/**").permitAll()

                    .anyRequest().authenticated()
            }

            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .logout { it.disable() }

            .exceptionHandling {
                it.authenticationEntryPoint(authenticationEntryPoint)
                    .accessDeniedHandler(accessDeniedHandler)
            }

        return http.build()
    }

}