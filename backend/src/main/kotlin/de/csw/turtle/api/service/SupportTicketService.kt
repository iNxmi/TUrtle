package de.csw.turtle.api.service

import de.csw.turtle.api.entity.SupportTicketEntity
import de.csw.turtle.api.entity.SupportTicketEntity.*
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.repository.SupportTicketRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class SupportTicketService(
    override val repository: SupportTicketRepository
) : CRUDService<SupportTicketEntity>() {

    private val regex = ("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$").toRegex()
    private val maxSubjectLength = 64
    private val maxDescriptionLength = 2028

    @Transactional
    fun create(
        urgency: Urgency,
        category: Category,
        email: String,
        subject: String,
        description: String,
        status: Status
    ): SupportTicketEntity {

        if (subject.isBlank() || subject.length > maxSubjectLength)
            throw HttpException.BadRequest("Subject cannot be left blank and cannot be longer than $maxSubjectLength characters.")

        if (description.isBlank() || description.length > maxDescriptionLength)
            throw HttpException.BadRequest("Description cannot be left blank and cannot be longer than $maxDescriptionLength characters.")

        if (!regex.matches(email))
            throw HttpException.BadRequest("'${email}' is not a valid Email Address.")

        //TODO email is valid (regex)
        //TODO subject is not empty
        //TODO subject length is max 64
        //TODO description is not empty
        //TODO description max 2048 length

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

        if (subject != null)
            if (subject.isBlank() || subject.length > maxSubjectLength)
                throw HttpException.BadRequest("Subject cannot be left blank and cannot be longer than $maxSubjectLength characters.")

        if (description != null)
            if (description.isBlank() || description.length > maxDescriptionLength)
                throw HttpException.BadRequest("Description cannot be left blank and cannot be longer than $maxDescriptionLength characters.")

        if (email != null)
            if (!regex.matches(email))
                throw HttpException.BadRequest("'${email}' is not a valid Email Address.")

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