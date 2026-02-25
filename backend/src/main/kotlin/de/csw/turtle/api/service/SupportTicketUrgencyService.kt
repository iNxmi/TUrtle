package de.csw.turtle.api.service

import de.csw.turtle.api.entity.ConfigurationEntity.Key
import de.csw.turtle.api.entity.SupportTicketUrgencyEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.repository.SupportTicketUrgencyRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class SupportTicketUrgencyService(
    override val repository: SupportTicketUrgencyRepository,
    private val configurationService: ConfigurationService
) : CRUDService<SupportTicketUrgencyEntity>() {

    fun getByNameOrNull(name: String): SupportTicketUrgencyEntity? = repository.findByName(name)
    fun getByName(name: String) = repository.findByName(name) ?: throw HttpException.NotFound(name)

    @Transactional
    fun create(
        name: String
    ): SupportTicketUrgencyEntity {

        val maxNameLength = configurationService.getTyped<Int>(Key.SUPPORT_TICKET_URGENCY_NAME_LENGTH)
        if (name.isBlank() || name.length > maxNameLength)
            throw HttpException.BadRequest("Name cannot be blank or exceed $maxNameLength} characters.")

        if (repository.findByName(name) != null)
            throw HttpException.Conflict("Support ticket urgency with name '$name' already exists.")

        val entity = SupportTicketUrgencyEntity(name = name)
        return repository.save(entity)
    }

    @Transactional
    fun patch(
        id: Long,
        name: String? = null
    ): SupportTicketUrgencyEntity {
        val maxNameLength = configurationService.getTyped<Int>(Key.SUPPORT_TICKET_URGENCY_NAME_LENGTH)
        if (name != null) {
            if (name.isBlank() || name.length > maxNameLength)
                throw HttpException.BadRequest("Name cannot be blank or exceed $maxNameLength characters.")

            if (repository.findByName(name) != null)
                throw HttpException.Conflict("Support ticket urgency with name '$name' already exists.")
        }

        val entity = repository.findById(id).get()

        name?.let { entity.name = it }

        return repository.save(entity)
    }

}