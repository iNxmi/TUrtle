package de.csw.turtle.api.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.thymeleaf.TemplateEngine
import org.thymeleaf.spring6.SpringTemplateEngine
import org.thymeleaf.templatemode.TemplateMode
import org.thymeleaf.templateresolver.StringTemplateResolver

@Configuration
class ThymeleafConfig {

    @Bean
    @Primary
    fun customTemplateEngine(): TemplateEngine {
        val engine = SpringTemplateEngine()

        val htmlResolver = StringTemplateResolver()
        htmlResolver.templateMode = TemplateMode.HTML
        htmlResolver.isCacheable = false
        htmlResolver.order = 1

        val textResolver = StringTemplateResolver()
        textResolver.templateMode = TemplateMode.TEXT
        textResolver.isCacheable = false
        textResolver.order = 2

        engine.addTemplateResolver(htmlResolver)
        engine.addTemplateResolver(textResolver)

        return engine
    }

}