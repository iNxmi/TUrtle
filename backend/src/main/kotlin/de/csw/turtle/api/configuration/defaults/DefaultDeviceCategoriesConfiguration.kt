package de.csw.turtle.api.configuration.defaults

import de.csw.turtle.api.dto.create.CreateDeviceCategoryRequest
import de.csw.turtle.api.service.DeviceCategoryService
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.Transactional

@Configuration
class DefaultDeviceCategoriesConfiguration(
    private val service: DeviceCategoryService
) : CommandLineRunner {

    @Transactional
    override fun run(vararg args: String) {
        if (service.count() > 0)
            return

        service.create(CreateDeviceCategoryRequest("Beamer"))
        service.create(CreateDeviceCategoryRequest("Laptop"))
        service.create(CreateDeviceCategoryRequest("Tablet"))
        service.create(CreateDeviceCategoryRequest("Lernmedium"))
        service.create(CreateDeviceCategoryRequest("Gaming"))
        service.create(CreateDeviceCategoryRequest("Streaming"))
        service.create(CreateDeviceCategoryRequest("Zubehör"))
    }

}