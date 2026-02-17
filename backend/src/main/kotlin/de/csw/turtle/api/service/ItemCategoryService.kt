package de.csw.turtle.api.service

import de.csw.turtle.api.entity.ConfigurationEntity
import de.csw.turtle.api.entity.ItemCategoryEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.repository.ItemCategoryRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class ItemCategoryService(
    override val repository: ItemCategoryRepository,
    private val configurationService: ConfigurationService
) : CRUDService<ItemCategoryEntity>() {

    fun getByNameOrNull(name: String): ItemCategoryEntity? = repository.findByName(name)
    fun getByName(name: String) = repository.findByName(name) ?: throw HttpException.NotFound(name)

    @Transactional
    fun create(
        name: String
    ): ItemCategoryEntity {

        val maxNameLength = configurationService.getTyped<Int>(ConfigurationEntity.Key.ITEM_CATEGORY_NAME_LENGTH)
        if(name.isBlank() || name.length > maxNameLength)
            throw HttpException.BadRequest("Name cannot be blank or exceed $maxNameLength} characters.")


        if (repository.findByName(name) != null)
            throw HttpException.Conflict("Item category with name '$name' already exists.")

        val entity = ItemCategoryEntity(name = name)
        return repository.save(entity)
    }

    @Transactional
    fun patch(
        id: Long,
        name: String? = null
    ): ItemCategoryEntity {
        val maxNameLength = configurationService.getTyped<Int>(ConfigurationEntity.Key.ITEM_CATEGORY_NAME_LENGTH)
        if (name != null) {
            if (name.isBlank() || name.length > maxNameLength)
                throw HttpException.BadRequest("Name cannot be blank or exceed $maxNameLength characters.")
            if (repository.findByName(name) != null)
                throw HttpException.Conflict("Item category with name '$name' already exists.")
        }

        val entity = repository.findById(id).get()

        name?.let { entity.name = it }

        return repository.save(entity)
    }

}