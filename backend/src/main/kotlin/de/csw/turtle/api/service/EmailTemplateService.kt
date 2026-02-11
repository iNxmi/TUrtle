package de.csw.turtle.api.service

import de.csw.turtle.api.entity.EmailTemplateEntity
import de.csw.turtle.api.repository.EmailTemplateRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class EmailTemplateService(
    override val repository: EmailTemplateRepository
) : CRUDService<EmailTemplateEntity>() {

    fun getByName(name: String): EmailTemplateEntity? = repository.findByName(name)
    fun getByType(type: EmailTemplateEntity.Type): EmailTemplateEntity? = repository.findByType(type)

    @Transactional
    fun create(
        name: String,
        description: String,
        subject: String,
        content: String,
        type: EmailTemplateEntity.Type?
    ): EmailTemplateEntity {
        val entity = EmailTemplateEntity(
            name = name,
            description = description,
            subject = subject,
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
        subject: String? = null,
        content: String? = null,
        type: EmailTemplateEntity.Type? = null,
    ): EmailTemplateEntity {
        val entity = repository.findById(id).get()

        name?.let { entity.name = it }
        description?.let { entity.description = it }
        subject?.let { entity.subject = it }
        content?.let { entity.content = it }
        type?.let { entity.type = it }

        return repository.save(entity)
    }

}