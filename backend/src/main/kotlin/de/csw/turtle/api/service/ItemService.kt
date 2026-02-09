package de.csw.turtle.api.service

import de.csw.turtle.api.entity.ItemEntity
import de.csw.turtle.api.repository.ItemCategoryRepository
import de.csw.turtle.api.repository.ItemRepository
import de.csw.turtle.api.repository.LockerRepository
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class ItemService(
    override val repository: ItemRepository,
    private val itemCategoryRepository: ItemCategoryRepository,
    private val lockerRepository: LockerRepository
) : CRUDService<ItemEntity>() {


    fun getByNameOrNull(name: String): ItemEntity? = repository.findByName(name)

    fun create(
        name: String,
        description: String,
        categoryId: Long,
        lockerId: Long,
        needsConfirmation: Boolean,
        acquiredAt: Instant
    ): ItemEntity {
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

        name?.let { entity.name = it }
        description?.let { entity.description = it }
        categoryId?.let { entity.category = itemCategoryRepository.findById(categoryId).get() }
        lockerId?.let { entity.locker = lockerRepository.findById(lockerId).get() }
        needsConfirmation?.let { entity.needsConfirmation = it }
        acquiredAt?.let { entity.acquiredAt = it }

        return repository.save(entity)
    }

}