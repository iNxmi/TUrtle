package de.csw.turtle.api.configuration

import de.csw.turtle.api.components.CurrentUserArgumentResolver
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.ForwardedHeaderFilter
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
class WebConfiguration(
    private val currentUserArgumentResolver: CurrentUserArgumentResolver,
) : WebMvcConfigurer {

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(currentUserArgumentResolver)
    }

}