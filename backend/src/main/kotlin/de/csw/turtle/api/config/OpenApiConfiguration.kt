package de.csw.turtle.api.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfiguration {

    @Bean
    fun customOpenApi(): OpenAPI {
        val info = Info().apply {
            title("TUrtleAPI")
            version("v1")
        }

        return OpenAPI().info(info)
    }

}