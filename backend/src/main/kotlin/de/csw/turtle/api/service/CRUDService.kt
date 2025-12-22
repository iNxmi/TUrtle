package de.csw.turtle.api.service

import de.csw.turtle.api.entity.CRUDEntity
import de.csw.turtle.api.exception.exceptions.crud.CRUDResourceNotFoundException
import de.csw.turtle.api.mapper.CRUDMapper
import de.csw.turtle.api.repository.CRUDRepository
import io.github.perplexhub.rsql.RSQLJPASupport
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
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

    open fun getOrNull(id: Long): Entity? = repository.findById(id).getOrNull()
    open fun get(id: Long): Entity = getOrNull(id) ?: throw CRUDResourceNotFoundException(name, id)

    open fun getAll(
        sort: Sort = Sort.unsorted(),
        rsql: String? = null
    ): Collection<Entity> {
        val specification = rsql?.let { RSQLJPASupport.toSpecification<Entity>(it) }
        return repository.findAll(specification, sort)
    }

    open fun getPage(
        pageable: Pageable,
        rsql: String? = null
    ): Page<Entity> {
        val specification = rsql?.let { RSQLJPASupport.toSpecification<Entity>(it) }
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