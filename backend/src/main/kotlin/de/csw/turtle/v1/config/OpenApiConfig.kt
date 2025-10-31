package de.csw.turtle.v1.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun customOpenApi(): OpenAPI {
        val info = Info().apply {
            title("TUrtleAPI")
            version("v1")
        }

        return OpenAPI().info(info)
    }

}