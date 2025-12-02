package de.csw.turtle.api.config

import org.springframework.boot.convert.DurationStyle
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import java.time.Duration

@Configuration
class DurationConverterConfig {

    @Bean
    fun durationConverter(): Converter<String, Duration> = Converter { source ->
        DurationStyle.detectAndParse(source)
    }

}
