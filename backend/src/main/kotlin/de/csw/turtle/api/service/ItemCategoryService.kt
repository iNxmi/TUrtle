package de.csw.turtle.api.service

import de.csw.turtle.api.entity.ItemCategoryEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.repository.ItemCategoryRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class ItemCategoryService(
    override val repository: ItemCategoryRepository
) : CRUDService<ItemCategoryEntity>() {
    fun getByNameOrNull(name: String): ItemCategoryEntity? = repository.findByName(name)
    fun getByName(name: String) = repository.findByName(name) ?: throw HttpException.NotFound(name)

    @Transactional
    fun create(
        name: String
    ): ItemCategoryEntity {
        val entity = ItemCategoryEntity(name = name)
        return repository.save(entity)
    }

    @Transactional
    fun patch(
        id: Long,
        name: String? = null
    ): ItemCategoryEntity {
        val entity = repository.findById(id).get()

        name?.let { entity.name = it }

        return repository.save(entity)
    }

}