package de.csw.turtle.api.configuration.defaults

import de.csw.turtle.api.service.SupportTicketCategoryService
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.Transactional

@Configuration
class DefaultSupportTicketCategories(
    private val service: SupportTicketCategoryService
) : CommandLineRunner {

    @Transactional
    override fun run(vararg args: String) {
        if (service.count() > 0)
            return

        service.create("Technical")
        service.create("Billing")
        service.create("General")
        service.create("Other")
    }

}