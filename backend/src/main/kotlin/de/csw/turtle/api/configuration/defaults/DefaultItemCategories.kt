package de.csw.turtle.api.configuration.defaults

import de.csw.turtle.api.service.ItemCategoryService
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.Transactional

@Configuration
class DefaultItemCategories(
    private val service: ItemCategoryService
) : CommandLineRunner {

    @Transactional
    override fun run(vararg args: String) {
        if (service.count() > 0)
            return

        service.create("Beamer")
        service.create("Laptop")
        service.create("Tablet")
        service.create("Lernmedium")
        service.create("Gaming")
        service.create("Streaming")
        service.create("Zubehör")
    }

}