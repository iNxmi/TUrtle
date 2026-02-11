package de.csw.turtle.api.listener

import de.csw.turtle.api.Settings
import de.csw.turtle.api.entity.UserEntity.Status
import de.csw.turtle.api.event.CreatedUserEvent
import de.csw.turtle.api.service.EmailService
import de.csw.turtle.api.service.EmailTemplateService
import de.csw.turtle.api.service.SystemSettingService
import de.csw.turtle.api.service.ThymeleafService
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.thymeleaf.context.Context
import java.time.Duration

@Component
class UserEmailListener(
    private val emailService: EmailService,
    private val systemSettingService: SystemSettingService,
    private val emailTemplateService: EmailTemplateService,
    private val thymeleafService: ThymeleafService
) {

    @Async
    @EventListener
    fun sendCreatedEmail(event: CreatedUserEvent) {
        val entity = event.entity

        val context = Context().apply {
            setVariable("user", entity)
        }

        val templateId = systemSettingService.getTyped<Long>(Settings.EMAIL_TEMPLATE_USERS_CREATED)
        val template = emailTemplateService.getById(templateId)
            ?: throw NoSuchElementException()

        val subject = thymeleafService.getRendered(template.subject, context)
        val content = thymeleafService.getRendered(template.content, context)
        emailService.sendHtmlEmail(entity.email, subject, content)
    }

}