package de.csw.turtle.api.service

import de.csw.turtle.api.entity.GeneralTemplateEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.repository.GeneralTemplateRepository
import org.springframework.stereotype.Service

@Service
class GeneralTemplateService(
    override val repository: GeneralTemplateRepository
) : CRUDService<GeneralTemplateEntity>() {

    fun getByNameOrNull(name: String): GeneralTemplateEntity? = repository.findByName(name)
    fun getByName(name: String): GeneralTemplateEntity =
        repository.findByName(name) ?: throw HttpException.NotFound(name)

    fun create(
        name: String,
        description: String,
        content: String
    ): GeneralTemplateEntity {
        val entity = GeneralTemplateEntity(
            name = name,
            description = description,
            content = content
        )

        return repository.save(entity)
    }

    fun patch(
        id: Long,
        name: String? = null,
        description: String? = null,
        content: String? = null
    ): GeneralTemplateEntity {
        val entity = repository.findById(id).get()

        name?.let { entity.name = it }
        description?.let { entity.description = it }
        content?.let { entity.content = it }

        return repository.save(entity)
    }

}