package de.csw.turtle.api.service.locker

import de.csw.turtle.api.entity.ItemEntity
import de.csw.turtle.api.entity.LockerEntity
import de.csw.turtle.api.repository.LockerRepository
import de.csw.turtle.api.service.CRUDService
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class LockerService(
    override val repository: LockerRepository
) : CRUDService<LockerEntity>() {

    fun getByIndex(index: Int) = repository.findByIndex(index)

    @Transactional
    fun create(
        name: String,
        index: Int,
        isSoftwareUnlockable: Boolean,
        locked: Boolean,
        items: MutableSet<ItemEntity> = mutableSetOf()
    ): LockerEntity {
        val entity = LockerEntity(
            name = name,
            index = index,
            isSoftwareUnlockable = isSoftwareUnlockable,
            locked = locked,
            items = items
        )

        return repository.save(entity)
    }

    @Transactional
    fun patch(
        id: Long,
        name: String? = null,
        index: Int? = null,
        isSoftwareUnlockable: Boolean? = null,
        locked: Boolean? = null,
        items: MutableSet<ItemEntity>? = null
    ): LockerEntity {
        val entity = getById(id)

        name?.let { entity.name = it }

        val updated = LockerEntity(
            name = name,
            index = index,
            isSoftwareUnlockable = isSoftwareUnlockable,
            locked = locked,
            items = items
        )

        return repository.save(updated)
    }

}