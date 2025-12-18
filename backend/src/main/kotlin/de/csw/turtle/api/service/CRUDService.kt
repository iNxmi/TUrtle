package de.csw.turtle.api.service

import de.csw.turtle.api.entity.CRUDEntity
import de.csw.turtle.api.exception.exceptions.crud.CRUDResourceNotFoundException
import de.csw.turtle.api.mapper.CRUDMapper
import de.csw.turtle.api.repository.CRUDRepository
import io.github.perplexhub.rsql.RSQLJPASupport
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort.Direction
import org.springframework.data.jpa.domain.Specification
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull


abstract class CRUDService<
        Entity : CRUDEntity,
        CreateRequest : de.csw.turtle.api.dto.create.CreateRequest,
        GetResponse : de.csw.turtle.api.dto.get.GetResponse,
        PatchRequest : de.csw.turtle.api.dto.patch.PatchRequest
        >(private val name: String) {

    abstract val repository: CRUDRepository<Entity>
    abstract val mapper: CRUDMapper<Entity, CreateRequest, GetResponse, PatchRequest>

    @Transactional
    open fun create(request: CreateRequest): Entity {
        val entity = mapper.create(request)
        return repository.save(entity)
    }

    fun getOrNull(id: Long): Entity? = repository.findById(id).getOrNull()
    fun get(id: Long): Entity = getOrNull(id) ?: throw CRUDResourceNotFoundException(name, id)

    fun getAll(
        filter: String? = null
    ): List<Entity> {
        val specification: Specification<Entity>? = filter?.let { RSQLJPASupport.toSpecification(it) }
        return repository.findAll(specification)
    }

    fun getPage(
        page: Int = 0,
        size: Int = 0,
        sort: Array<String> = emptyArray(),
        direction: Direction = Direction.DESC,
        filter: String? = null
    ): Page<Entity> {
        val specification: Specification<Entity>? = filter?.let { RSQLJPASupport.toSpecification(it) }

        val pageable = if (sort.isEmpty()) {
            PageRequest.of(page, size)
        } else {
            PageRequest.of(page, size, direction, *sort)
        }

        return repository.findAll(specification, pageable)
    }

    @Transactional
    open fun patch(id: Long, request: PatchRequest): Entity {
        val entity = get(id)
        val updated = mapper.patch(entity, request)
        return repository.save(updated)
    }

    @Transactional
    open fun delete(id: Long) = repository.delete(get(id))

    open fun count() = repository.count()

}