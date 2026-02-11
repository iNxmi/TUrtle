package de.csw.turtle.api.listener

import de.csw.turtle.api.Settings
import de.csw.turtle.api.entity.EmailTemplateEntity
import de.csw.turtle.api.entity.RoomBookingEntity
import de.csw.turtle.api.event.CreatedRoomBookingEvent
import de.csw.turtle.api.event.PatchedRoomBookingEvent
import de.csw.turtle.api.service.EmailService
import de.csw.turtle.api.service.EmailTemplateService
import de.csw.turtle.api.service.SystemSettingService
import de.csw.turtle.api.service.ThymeleafService
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.thymeleaf.context.Context

@Component
class RoomBookingEmailListener(
    private val emailService: EmailService,
    private val systemSettingService: SystemSettingService,
    private val emailTemplateService: EmailTemplateService,
    private val thymeleafService: ThymeleafService
) {

    @Async
    @EventListener
    fun sendCreatedEmail(event: CreatedRoomBookingEvent) {
        val entity = event.entity

        val templateId = systemSettingService.getTyped<Long>(Settings.EMAIL_TEMPLATE_ROOM_BOOKINGS_CREATED)
        val template = emailTemplateService.getById(templateId)
            ?: throw NoSuchElementException()

        val context = Context().apply {
            setVariable("booking", entity)
        }

        emailService.send(entity.user.email, template, context)
    }

    @Async
    @EventListener
    fun sendUpdatedEmail(event: PatchedRoomBookingEvent) {
        val templateId = systemSettingService.getTyped<Long>(Settings.EMAIL_TEMPLATE_ROOM_BOOKINGS_UPDATED)
        val template = emailTemplateService.getById(templateId)
            ?: throw NoSuchElementException()

        val pre = event.pre

        val context = Context().apply {
            setVariable("pre", pre)
            setVariable("post", event.post)
        }

        emailService.send(pre.user.email, template, context)
    }

}