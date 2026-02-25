package de.csw.turtle.api.service

import de.csw.turtle.api.entity.ConfigurationEntity
import de.csw.turtle.api.entity.SupportTicketEntity
import de.csw.turtle.api.entity.SupportTicketEntity.Status
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.repository.SupportTicketCategoryRepository
import de.csw.turtle.api.repository.SupportTicketRepository
import de.csw.turtle.api.repository.SupportTicketUrgencyRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class SupportTicketService(
    override val repository: SupportTicketRepository,
    private val supportTicketCategoryRepository: SupportTicketCategoryRepository,
    private val supportTicketUrgencyRepository: SupportTicketUrgencyRepository,
    private val configurationService: ConfigurationService
) : CRUDService<SupportTicketEntity>() {

    private val regex = ("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$").toRegex()

    @Transactional
    fun create(
        urgencyId: Long,
        categoryId: Long,
        email: String,
        subject: String,
        content: String,
        status: Status
    ): SupportTicketEntity {

        val maxSubjectLength = configurationService.getTyped<Int>(ConfigurationEntity.Key.SUPPORT_TICKET_SUBJECT_LENGTH)
        if (subject.isBlank() || subject.length > maxSubjectLength)
            throw HttpException.BadRequest("Subject cannot be left blank and cannot be longer than $maxSubjectLength characters.")

        val maxDescriptionLength =
            configurationService.getTyped<Int>(ConfigurationEntity.Key.SUPPORT_TICKET_DESCRIPTION_LENGTH)
        if (content.isBlank() || content.length > maxDescriptionLength)
            throw HttpException.BadRequest("Content cannot be left blank and cannot be longer than $maxDescriptionLength characters.")

        if (!regex.matches(email))
            throw HttpException.BadRequest("'${email}' is not a valid Email Address.")

        val entity = SupportTicketEntity(
            urgency = supportTicketUrgencyRepository.findById(urgencyId).get(),
            category = supportTicketCategoryRepository.findById(categoryId).get(),
            email = email,
            subject = subject,
            content = content,
            status = status
        )

        return repository.save(entity)
    }

    @Transactional
    fun patch(
        id: Long,
        urgencyId: Long? = null,
        categoryId: Long? = null,
        email: String? = null,
        subject: String? = null,
        content: String? = null,
        status: Status? = null
    ): SupportTicketEntity {

        val maxSubjectLength = configurationService.getTyped<Int>(ConfigurationEntity.Key.SUPPORT_TICKET_SUBJECT_LENGTH)
        if (subject != null)
            if (subject.isBlank() || subject.length > maxSubjectLength)
                throw HttpException.BadRequest("Subject cannot be left blank and cannot be longer than $maxSubjectLength} characters.")

        val maxDescriptionLength =
            configurationService.getTyped<Int>(ConfigurationEntity.Key.SUPPORT_TICKET_DESCRIPTION_LENGTH)
        if (content != null)
            if (content.isBlank() || content.length > maxDescriptionLength)
                throw HttpException.BadRequest("Description cannot be left blank and cannot be longer than $maxDescriptionLength characters.")

        if (email != null)
            if (!regex.matches(email))
                throw HttpException.BadRequest("'${email}' is not a valid Email Address.")

        val entity = repository.findById(id).get()

        urgencyId?.let { entity.urgency = supportTicketUrgencyRepository.findById(urgencyId).get() }
        categoryId?.let { entity.category = supportTicketCategoryRepository.findById(categoryId).get() }
        email?.let { entity.email = it }
        subject?.let { entity.subject = it }
        content?.let { entity.content = it }
        status?.let { entity.status = it }

        return repository.save(entity)
    }

}