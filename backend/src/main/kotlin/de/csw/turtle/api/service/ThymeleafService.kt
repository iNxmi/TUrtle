package de.csw.turtle.api.service

import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

@Service
class ThymeleafService(
    private val templateEngine: TemplateEngine
) {

    fun getRendered(template: String, context: Context): String = templateEngine.process(template, context)

}