package de.csw.turtle.api.listener

import de.csw.turtle.api.entity.EmailTemplateEntity.Type
import de.csw.turtle.api.event.CreatedRoomBookingEvent
import de.csw.turtle.api.event.PatchedRoomBookingEvent
import de.csw.turtle.api.service.EmailService
import de.csw.turtle.api.service.EmailTemplateService
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.thymeleaf.context.Context

@Component
class RoomBookingEmailListener(
    private val emailService: EmailService,
    private val emailTemplateService: EmailTemplateService
) {

    @Async
    @EventListener
    fun sendCreatedEmail(event: CreatedRoomBookingEvent) {
        val entity = event.entity

        val template = emailTemplateService.getByType(Type.ROOM_BOOKING_CREATED)
            ?: throw NoSuchElementException()

        val context = Context().apply {
            setVariable("booking", entity)
        }

        emailService.send(entity.user.email, template, context)
    }

    @Async
    @EventListener
    fun sendUpdatedEmail(event: PatchedRoomBookingEvent) {
        val template = emailTemplateService.getByType(Type.ROOM_BOOKING_UPDATED)
            ?: throw NoSuchElementException()

        val pre = event.pre

        val context = Context().apply {
            setVariable("pre", pre)
            setVariable("post", event.post)
        }

        emailService.send(pre.user.email, template, context)
    }

}