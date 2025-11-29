package de.csw.turtle.api.service

import de.csw.turtle.api.dto.create.CRUDCreateRequest
import de.csw.turtle.api.dto.get.CRUDGetResponse
import de.csw.turtle.api.dto.patch.CRUDPatchRequest
import de.csw.turtle.api.entity.CRUDEntity
import de.csw.turtle.api.mapper.CRUDMapper
import de.csw.turtle.api.repository.CRUDRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort.Direction
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

abstract class CRUDService<
        Entity : CRUDEntity,
        CreateRequest : CRUDCreateRequest,
        GetResponse : CRUDGetResponse,
        PatchRequest : CRUDPatchRequest
        > {

    protected abstract val repository: CRUDRepository<Entity>
    protected abstract val mapper: CRUDMapper<Entity, CreateRequest, GetResponse, PatchRequest>

    @Transactional
    open fun create(request: CreateRequest): Entity {
        val entity = mapper.create(request)
        return repository.save(entity)
    }

    fun getOrNull(id: Long): Entity? = repository.findById(id).getOrNull()
    fun get(id: Long): Entity = getOrNull(id) ?: TODO("implement exception")

    fun getAll(): List<Entity> = repository.findAll()

    fun getPage(
        page: Int = 0,
        size: Int = 0,
        sort: Array<String> = emptyArray(),
        direction: Direction = Direction.DESC
    ): Page<Entity> {
        val request = if (sort.isEmpty()) {
            PageRequest.of(page, size)
        } else {
            PageRequest.of(page, size, direction, *sort)
        }

        return repository.findAll(request)
    }

    @Transactional
    open fun patch(id: Long, request: PatchRequest): Entity {
        val entity = mapper.patch(get(id), request)
        return repository.save(entity)
    }

    @Transactional
    open fun delete(id: Long) = repository.delete(get(id))

    open fun count() = repository.count()

}