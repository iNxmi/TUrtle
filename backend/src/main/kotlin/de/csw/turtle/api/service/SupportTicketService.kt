package de.csw.turtle.api.service

import de.csw.turtle.api.entity.SupportTicketEntity
import de.csw.turtle.api.entity.SupportTicketEntity.*
import de.csw.turtle.api.repository.SupportTicketRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class SupportTicketService(
    override val repository: SupportTicketRepository
) : CRUDService<SupportTicketEntity>() {

    @Transactional
    fun create(
        urgency: Urgency,
        category: Category,
        email: String,
        subject: String,
        description: String,
        status: Status
    ): SupportTicketEntity {
        val entity = SupportTicketEntity(
            urgency = urgency,
            category = category,
            email = email,
            subject = subject,
            description = description,
            status = status
        )

        return repository.save(entity)
    }

    @Transactional
    fun patch(
        id: Long,
        urgency: Urgency? = null,
        category: Category? = null,
        email: String? = null,
        subject: String? = null,
        description: String? = null,
        status: Status? = null
    ): SupportTicketEntity {
        val entity = repository.findById(id).get()

        urgency?.let { entity.urgency = it }
        category?.let { entity.category = it }
        email?.let { entity.email = it }
        subject?.let { entity.subject = it }
        description?.let { entity.description = it }
        status?.let { entity.status = it }

        return repository.save(entity)
    }

}