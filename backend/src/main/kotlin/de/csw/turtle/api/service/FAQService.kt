package de.csw.turtle.api.service

import de.csw.turtle.api.entity.FAQEntity
import de.csw.turtle.api.repository.FAQRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class FAQService(
    override val repository: FAQRepository
) : CRUDService<FAQEntity>() {

    fun getByName(name: String): FAQEntity? = repository.findByName(name)

    @Transactional
    fun create(
        name: String,
        title: String,
        content: String,
        enabled: Boolean
    ): FAQEntity {
        val entity = FAQEntity(
            name = name,
            title = title,
            content = content,
            enabled = enabled
        )
        return repository.save(entity)
    }

    fun patch(
        id: Long,
        name: String? = null,
        title: String? = null,
        content: String? = null,
        enabled: Boolean? = null
    ): FAQEntity {
        val entity = repository.findById(id).get()

        name?.let { entity.name = it }
        title?.let { entity.title = it }
        content?.let { entity.content = it }
        enabled?.let { entity.enabled = it }

        return repository.save(entity)
    }

}