package de.csw.turtle.api.configuration.defaults

import de.csw.turtle.api.service.SupportTicketCategoryService
import de.csw.turtle.api.service.SupportTicketUrgencyService
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.Transactional

@Configuration
class DefaultSupportTicketUrgencies(
    private val service: SupportTicketUrgencyService
) : CommandLineRunner {

    @Transactional
    override fun run(vararg args: String) {
        if (service.count() > 0)
            return

        service.create("Low")
        service.create("Medium")
        service.create("High")
        service.create("Critical")
    }

}