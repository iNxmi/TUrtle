package de.csw.turtle.api.service

import de.csw.turtle.api.dto.create.CreateLockerRequest
import de.csw.turtle.api.dto.patch.PatchLockerRequest
import de.csw.turtle.api.entity.LockerEntity
import de.csw.turtle.api.exception.exceptions.locker.IndexAlreadyExistsException
import de.csw.turtle.api.exception.exceptions.locker.LockerNotFoundException
import de.csw.turtle.api.repository.LockerRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
class LockerService(
    private val repository: LockerRepository
) {

    @Transactional
    fun create(request: CreateLockerRequest): LockerEntity {
        if (repository.findByIndex(request.index) != null)
            throw IndexAlreadyExistsException(request.index)

        val entity = request.create()
        return repository.save(entity)
    }

    //TODO make it not query for hidden fields based on dto (only be able to sort by dto fields)
    fun getPaginated(request: PageRequest) = repository.findAll(request)
    fun get(id: Long) = repository.findById(id).getOrNull()

    @Transactional
    fun update(id: Long, request: PatchLockerRequest): LockerEntity {
        val entity = repository.findById(id).getOrNull()
            ?: throw LockerNotFoundException(id)

        request.index?.let { entity.index = it }
        request.name?.let { entity.name = it }

        return repository.save(entity)
    }

    @Transactional
    fun delete(id: Long) {
        val entity = repository.findById(id).getOrNull()
            ?: throw LockerNotFoundException(id)

        repository.delete(entity)
    }

}