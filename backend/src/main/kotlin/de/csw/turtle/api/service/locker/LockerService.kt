package de.csw.turtle.api.service.locker

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
        locked: Boolean
    ): LockerEntity {
        val entity = LockerEntity(
            name = name,
            index = index,
            isSoftwareUnlockable = isSoftwareUnlockable,
            locked = locked
        )

        return repository.save(entity)
    }

    @Transactional
    fun patch(
        id: Long,
        name: String? = null,
        index: Int? = null,
        isSoftwareUnlockable: Boolean? = null,
        locked: Boolean? = null
    ): LockerEntity {
        val entity = repository.findById(id).get()

        name?.let { entity.name = it }
        index?.let { entity.index = it }
        isSoftwareUnlockable?.let { entity.isSoftwareUnlockable = it }
        locked?.let { entity.locked = it }

        return repository.save(entity)
    }

}