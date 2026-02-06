package de.csw.turtle.api.configuration.defaults

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
        Welcome <span th:text="${'$'}{user.firstName}">user.firstName</span>,
        <br>
        You have successfully created your CSW account.
        <br>
        To verify your account please click <a th:href="${'$'}{url}">here</a>.
        <br>
        If you don't verify in <span th:text="${'$'}{duration.toDays()}">duration.toDays()</span> Days (<span th:text="${'$'}{user.createdAt.plusMillis(duration.toMillis())}">user.createdAt.plusMillis(duration.toMillis())</span>), your account will be deleted.
    """.trimIndent()

    @Transactional
    override fun run(vararg args: String) {
        if (service.count() > 0)
            return

        service.create("verify", "default value by dev", "CSW - Please verify your account", verify)
    }

}