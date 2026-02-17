package de.csw.turtle.api.service

import de.csw.turtle.api.entity.ItemEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.repository.ItemCategoryRepository
import de.csw.turtle.api.repository.ItemRepository
import de.csw.turtle.api.repository.LockerRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class ItemService(
    override val repository: ItemRepository,
    private val itemCategoryRepository: ItemCategoryRepository,
    private val lockerRepository: LockerRepository
) : CRUDService<ItemEntity>() {

    private val maxNameLength = 64
    private val maxDescriptionLength = 256

    fun getByNameOrNull(name: String): ItemEntity? = repository.findByName(name)

    @Transactional
    fun create(
        name: String,
        description: String,
        categoryId: Long,
        lockerId: Long,
        needsConfirmation: Boolean,
        acquiredAt: Instant
    ): ItemEntity {

        if (name.isBlank() || name.length > maxNameLength)
            throw HttpException.BadRequest("Name cannot be blank and cannot exceed $maxNameLength characters.")

        if (repository.findByName(name) != null)
            throw HttpException.Conflict("Name '${name}' already exists.")

        if (description.length > maxDescriptionLength)
            throw HttpException.BadRequest("Description cannot exceed $maxDescriptionLength characters.")

        if(!itemCategoryRepository.existsById(categoryId))
            throw HttpException.BadRequest("Category with ID $categoryId does not exist.")

        if(!lockerRepository.existsById(lockerId))
            throw HttpException.BadRequest("Locker with ID $lockerId does not exist.")

        val entity = ItemEntity(
            name = name,
            description = description,
            category = itemCategoryRepository.findById(categoryId).get(),
            locker = lockerRepository.findById(lockerId).get(),
            needsConfirmation = needsConfirmation,
            acquiredAt = acquiredAt
        )

        return repository.save(entity)
    }

    @Transactional
    fun patch(
        id: Long,
        name: String? = null,
        description: String? = null,
        categoryId: Long? = null,
        lockerId: Long? = null,
        needsConfirmation: Boolean? = null,
        acquiredAt: Instant? = null
    ): ItemEntity {
        val entity = repository.findById(id).get()

        if (name != null) {
            if (name.isBlank() || name.length > maxNameLength)
                throw HttpException.BadRequest("Name cannot be blank and cannot exceed $maxNameLength characters.")

            if (repository.findByName(name) != null)
                throw HttpException.Conflict("Name '${name}' already exists.")
        }

        if (description != null)
            if (description.length > maxDescriptionLength)
                throw HttpException.BadRequest("Description cannot exceed $maxDescriptionLength characters.")

        name?.let { entity.name = it }
        description?.let { entity.description = it }
        categoryId?.let { entity.category = itemCategoryRepository.findById(categoryId).get() }
        lockerId?.let { entity.locker = lockerRepository.findById(lockerId).get() }
        needsConfirmation?.let { entity.needsConfirmation = it }
        acquiredAt?.let { entity.acquiredAt = it }

        return repository.save(entity)
    }

}