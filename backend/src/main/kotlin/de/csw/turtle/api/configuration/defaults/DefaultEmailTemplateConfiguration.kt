package de.csw.turtle.api.configuration.defaults

import de.csw.turtle.api.dto.create.CreateEmailTemplateRequest
import de.csw.turtle.api.service.EmailTemplateService
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.transaction.annotation.Transactional

@Order(1)
@Configuration
class DefaultEmailTemplateConfiguration(
    private val service: EmailTemplateService
) : CommandLineRunner {

    val verify = """
        Welcome {{user.firstName}},
        You have successfully created your CSW account.
        To verify your account please click <a href=\"{{url}}\">here</a>.
        If you don't verify in {{duration.toDays()}} Days ({{expiration}}), your account will be deleted.
    """.trimIndent()

    @Transactional
    override fun run(vararg args: String) {
        if (service.count() > 0)
            return

        service.create(CreateEmailTemplateRequest("verify", "default value by dev", "CSW - Please verify your account", verify))
    }

}