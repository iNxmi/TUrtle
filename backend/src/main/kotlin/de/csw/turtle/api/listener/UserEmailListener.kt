package de.csw.turtle.api.listener

import de.csw.turtle.api.entity.EmailTemplateEntity.Type
import de.csw.turtle.api.event.CreatedUserEvent
import de.csw.turtle.api.service.EmailService
import de.csw.turtle.api.service.EmailTemplateService
import de.csw.turtle.api.service.ThymeleafService
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.thymeleaf.context.Context

@Component
class UserEmailListener(
    private val emailService: EmailService,
    private val emailTemplateService: EmailTemplateService,
    private val thymeleafService: ThymeleafService
) {

    @Async
    @EventListener
    fun sendCreatedEmail(event: CreatedUserEvent) {
        val template = emailTemplateService.getByType(Type.USER_CREATED)
            ?: throw NoSuchElementException()

        val entity = event.entity
        val context = Context().apply {
            setVariable("user", entity)
        }

        emailService.send(entity.email, template, context)
    }

}