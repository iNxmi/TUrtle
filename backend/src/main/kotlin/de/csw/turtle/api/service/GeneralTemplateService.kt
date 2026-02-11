package de.csw.turtle.api.service

import de.csw.turtle.api.entity.GeneralTemplateEntity
import de.csw.turtle.api.repository.GeneralTemplateRepository
import org.springframework.stereotype.Service

@Service
class GeneralTemplateService(
    override val repository: GeneralTemplateRepository
) : CRUDService<GeneralTemplateEntity>() {

    fun getByName(name: String): GeneralTemplateEntity? = repository.findByName(name)
    fun getByType(type: GeneralTemplateEntity.Type): GeneralTemplateEntity? = repository.findByType(type)

    fun create(
        name: String,
        description: String,
        content: String,
        type: GeneralTemplateEntity.Type?
    ): GeneralTemplateEntity {
        val entity = GeneralTemplateEntity(
            name = name,
            description = description,
            content = content,
            type = type
        )

        return repository.save(entity)
    }

    fun patch(
        id: Long,
        name: String? = null,
        description: String? = null,
        content: String? = null,
        type: GeneralTemplateEntity.Type? = null,
    ): GeneralTemplateEntity {
        val entity = repository.findById(id).get()

        name?.let { entity.name = it }
        description?.let { entity.description = it }
        content?.let { entity.content = it }
        type?.let { entity.type = it }

        return repository.save(entity)
    }

}