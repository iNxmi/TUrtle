package de.csw.turtle.api.v1.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.method.HandlerTypePredicate
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {

    override fun configurePathMatch(configurer: PathMatchConfigurer) {
        val predicate = HandlerTypePredicate
            .forBasePackage("com.csw.turtle.api.v1")
            .and(HandlerTypePredicate.forAnnotation(RestController::class.java))

        configurer.addPathPrefix("/api/v1", predicate)
    }

}