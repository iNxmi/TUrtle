package de.csw.turtle.api.configuration

import de.csw.turtle.api.filter.JWTAuthFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfiguration(
    private val jwtAuthFilter: JWTAuthFilter
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }
            .cors { it.disable() }

            .authorizeHttpRequests {
                it.requestMatchers(HttpMethod.GET, "/api/faq").permitAll()
                it.requestMatchers(HttpMethod.POST, "/api/support-tickets").permitAll()
                it.requestMatchers(HttpMethod.POST, "/api/hardware/door/emojis").permitAll()
                it.requestMatchers(HttpMethod.GET, "/api/devices").permitAll()
                it.requestMatchers(HttpMethod.GET, "/api/device-categories").permitAll()
                it.requestMatchers(HttpMethod.GET, "/api/content/**").permitAll()
                it.requestMatchers("/api/auth/**").permitAll()
                it.requestMatchers(HttpMethod.GET, "/docs/**").permitAll()
                it.requestMatchers(HttpMethod.GET, "/openapi/**").permitAll()
                it.requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
                it.anyRequest().authenticated()
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

}