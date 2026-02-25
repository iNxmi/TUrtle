package de.csw.turtle.api.service

import de.csw.turtle.api.entity.ConfigurationEntity.Key
import de.csw.turtle.api.entity.SupportTicketCategoryEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.repository.SupportTicketCategoryRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class SupportTicketCategoryService(
    override val repository: SupportTicketCategoryRepository,
    private val configurationService: ConfigurationService
) : CRUDService<SupportTicketCategoryEntity>() {

    fun getByNameOrNull(name: String): SupportTicketCategoryEntity? = repository.findByName(name)
    fun getByName(name: String) = repository.findByName(name) ?: throw HttpException.NotFound(name)

    @Transactional
    fun create(
        name: String
    ): SupportTicketCategoryEntity {

        val maxNameLength = configurationService.getTyped<Int>(Key.SUPPORT_TICKET_CATEGORY_NAME_LENGTH)
        if (name.isBlank() || name.length > maxNameLength)
            throw HttpException.BadRequest("Name cannot be blank or exceed $maxNameLength} characters.")

        if (repository.findByName(name) != null)
            throw HttpException.Conflict("Support ticket category with name '$name' already exists.")

        val entity = SupportTicketCategoryEntity(name = name)
        return repository.save(entity)
    }

    @Transactional
    fun patch(
        id: Long,
        name: String? = null
    ): SupportTicketCategoryEntity {
        val maxNameLength = configurationService.getTyped<Int>(Key.SUPPORT_TICKET_CATEGORY_NAME_LENGTH)
        if (name != null) {
            if (name.isBlank() || name.length > maxNameLength)
                throw HttpException.BadRequest("Name cannot be blank or exceed $maxNameLength characters.")

            if (repository.findByName(name) != null)
                throw HttpException.Conflict("Support ticket category with name '$name' already exists.")
        }

        val entity = repository.findById(id).get()

        name?.let { entity.name = it }

        return repository.save(entity)
    }

}