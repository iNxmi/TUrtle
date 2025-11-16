package de.csw.turtle

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.data.web.config.EnableSpringDataWebSupport
import kotlin.jvm.javaClass

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
@ConfigurationPropertiesScan
class TUrtleApplication

fun main(args: Array<String>) {
    runApplication<TUrtleApplication>(*args)
}
