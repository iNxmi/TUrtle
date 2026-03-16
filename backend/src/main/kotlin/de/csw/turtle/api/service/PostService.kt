package de.csw.turtle.api.service

import de.csw.turtle.api.entity.PostEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.repository.PostRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class PostService(
    override val repository: PostRepository
) : CRUDService<PostEntity>() {

    @Transactional
    fun create(
        name: String,
        description: String,
        title: String,
        content: String,
        enabled: Boolean
    ): PostEntity {
        val entity = PostEntity(
            name = name,
            description = description,
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
        description: String? = null,
        title: String? = null,
        content: String? = null,
        enabled: Boolean? = null
    ): PostEntity {
        val entity = repository.findById(id).getOrNull()
            ?: throw HttpException.NotFound("Post with id '$id' not found.")

        name?.let { entity.name = it }
        description?.let { entity.description = it }
        title?.let { entity.title = it }
        content?.let { entity.content = it }
        enabled?.let { entity.enabled = it }

        return repository.save(entity)
    }

}