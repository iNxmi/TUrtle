package de.csw.turtle.api.service

import de.csw.turtle.api.entity.ContentTemplateEntity
import de.csw.turtle.api.entity.ContentTemplateEntity.Type
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.repository.ContentTemplateRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class ContentTemplateService(
    override val repository: ContentTemplateRepository
) : CRUDService<ContentTemplateEntity>() {

    fun existsByType(type: Type): Boolean = repository.existsByType(type)
    fun getByName(name: String): ContentTemplateEntity? = repository.findByName(name)
    fun getByType(type: Type): ContentTemplateEntity? = repository.findByType(type)

    @Transactional
    fun create(
        name: String,
        description: String,
        content: String,
        type: Type?
    ): ContentTemplateEntity {
        if (repository.findByName(name) != null)
            throw HttpException.Conflict("General template with name '$name' already exists.")

        val entity = ContentTemplateEntity(
            name = name,
            description = description,
            content = content,
            type = type
        )

        return repository.save(entity)
    }

    @Transactional
    fun patch(
        id: Long,
        name: String? = null,
        description: String? = null,
        content: String? = null,
        type: Type? = null,
    ): ContentTemplateEntity {
        val entity = repository.findById(id).get()

        if (name != null)
            if (repository.findByName(name) != null)
                throw HttpException.Conflict("General template with name '$name' already exists.")

        name?.let { entity.name = it }
        description?.let { entity.description = it }
        content?.let { entity.content = it }
        type?.let { entity.type = it }

        return repository.save(entity)
    }

}