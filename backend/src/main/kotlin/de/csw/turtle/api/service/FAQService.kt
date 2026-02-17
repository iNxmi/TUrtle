package de.csw.turtle.api.service

import de.csw.turtle.api.entity.ConfigurationEntity
import de.csw.turtle.api.entity.FAQEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.repository.FAQRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class FAQService(
    override val repository: FAQRepository,
    private val configurationService: ConfigurationService
) : CRUDService<FAQEntity>() {

    fun getByName(name: String): FAQEntity? = repository.findByName(name)

    @Transactional
    fun create(
        name: String,
        title: String,
        content: String,
        enabled: Boolean
    ): FAQEntity {
        val maxNameLength = configurationService.getTyped<Int>(ConfigurationEntity.Key.FAQ_NAME_LENGTH)
        if (name.isBlank() || name.length > maxNameLength)
            throw HttpException.BadRequest("Name is required and cannot exceed $maxNameLength characters.")

        if (repository.findByName(name) != null)
            throw HttpException.Conflict("Name '$name' already exists.")

        val maxTitleLength = configurationService.getTyped<Int>(ConfigurationEntity.Key.FAQ_TITLE_LENGTH)
        if (title.isBlank() || title.length > maxTitleLength)
            throw HttpException.BadRequest("Title is required and cannot exceed $maxTitleLength characters.")

        if (content.isBlank())
            throw HttpException.BadRequest("Content is required.")

        val entity = FAQEntity(
            name = name,
            title = title,
            content = content,
            enabled = enabled
        )

        return repository.save(entity)
    }

    @Transactional
    fun patch(
        id: Long,
        name: String? = null,
        title: String? = null,
        content: String? = null,
        enabled: Boolean? = null
    ): FAQEntity {
        val entity = repository.findById(id).get()

        val maxNameLength = configurationService.getTyped<Int>(ConfigurationEntity.Key.FAQ_NAME_LENGTH)
        if (name != null) {
            if (name.isBlank() || name.length > maxNameLength)
                throw HttpException.BadRequest("Name is required and cannot exceed $maxNameLength characters.")

            if (repository.findByName(name) != null)
                throw HttpException.Conflict("Name '${name}' already exists.")
        }

        val maxTitleLength = configurationService.getTyped<Int>(ConfigurationEntity.Key.FAQ_TITLE_LENGTH)
        if (title != null)
            if (title.isBlank() || title.length > maxTitleLength)
                throw HttpException.BadRequest("Title is required and cannot exceed $maxTitleLength characters.")

        if (content != null)
            if (content.isBlank())
                throw HttpException.BadRequest("Content is required.")

        name?.let { entity.name = it }
        title?.let { entity.title = it }
        content?.let { entity.content = it }
        enabled?.let { entity.enabled = it }

        return repository.save(entity)
    }

}