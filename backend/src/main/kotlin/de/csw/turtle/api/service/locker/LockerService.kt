package de.csw.turtle.api.service.locker

import de.csw.turtle.api.entity.LockerEntity
import de.csw.turtle.api.repository.LockerRepository
import de.csw.turtle.api.service.CRUDService
import de.csw.turtle.api.exception.HttpException
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class LockerService(
    override val repository: LockerRepository
) : CRUDService<LockerEntity>() {

    private val maxNameLength = 64

    fun getByIndex(index: Int) = repository.findByIndex(index) ?: throw HttpException.NotFound("Locker with index '$index' not found.")

    fun getByName(name: String) = repository.findByName(name) ?: throw HttpException.NotFound("Locker with name '$name' not found.")

    @Transactional
    fun create(
        name: String,
        index: Int,
        isSoftwareUnlockable: Boolean,
        locked: Boolean
    ): LockerEntity {
        if (name.isBlank() || name.length > maxNameLength) {
            throw HttpException.BadRequest("Name cannot be blank or exceed $maxNameLength characters.")
        }
        if (repository.findByName(name) != null) {
            throw HttpException.Conflict("Locker with name '$name' already exists.")
        }
        if (repository.findByIndex(index) != null) {
            throw HttpException.Conflict("Locker with index '$index' already exists.")
        }

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
        val entity = repository.findById(id).getOrNull()
            ?: throw HttpException.NotFound("Locker with id '$id' not found.")

        name?.let {
            if (it.isBlank() || it.length > maxNameLength) {
                throw HttpException.BadRequest("Name cannot be blank or exceed $maxNameLength characters.")
            }
            if (it != entity.name && repository.findByName(it) != null) {
                throw HttpException.Conflict("Locker with name '$it' already exists.")
            }
            entity.name = it
        }

        index?.let {
            if (it != entity.index && repository.findByIndex(it) != null) {
                throw HttpException.Conflict("Locker with index '$it' already exists.")
            }
            entity.index = it
        }

        isSoftwareUnlockable?.let { entity.isSoftwareUnlockable = it }
        locked?.let { entity.locked = it }

        return repository.save(entity)
    }

}