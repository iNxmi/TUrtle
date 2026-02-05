package de.csw.turtle.api.configuration.defaults

import de.csw.turtle.api.dto.create.CreateItemCategoryRequest
import de.csw.turtle.api.service.ItemCategoryService
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.Transactional

@Configuration
class DefaultItemCategoriesConfiguration(
    private val service: ItemCategoryService
) : CommandLineRunner {

    @Transactional
    override fun run(vararg args: String) {
        if (service.count() > 0)
            return

        service.create(CreateItemCategoryRequest("Beamer"))
        service.create(CreateItemCategoryRequest("Laptop"))
        service.create(CreateItemCategoryRequest("Tablet"))
        service.create(CreateItemCategoryRequest("Lernmedium"))
        service.create(CreateItemCategoryRequest("Gaming"))
        service.create(CreateItemCategoryRequest("Streaming"))
        service.create(CreateItemCategoryRequest("Zubehör"))
    }

}