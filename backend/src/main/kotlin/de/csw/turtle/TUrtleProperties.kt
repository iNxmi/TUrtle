package de.csw.turtle

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

@ConfigurationProperties(prefix = "turtle")
data class TUrtleProperties(
    val security: Security
) {

    data class Security(
        val sessionSecret: String,
        val maxSessions: Int = 16,
        val sessionDuration: Duration = Duration.ofMinutes(5),
        val rememberMeDuration: Duration = Duration.ofDays(30),
    )

}