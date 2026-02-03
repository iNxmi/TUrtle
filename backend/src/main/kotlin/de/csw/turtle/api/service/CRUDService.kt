package de.csw.turtle.api.service

import cz.jirutka.rsql.parser.RSQLParserException
import de.csw.turtle.api.entity.CRUDEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.mapper.CRUDMapper
import de.csw.turtle.api.repository.CRUDRepository
import io.github.perplexhub.rsql.ConversionException
import io.github.perplexhub.rsql.RSQLJPASupport
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
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

    @Transactional
    open fun getOrNull(id: Long): Entity? = repository.findById(id).getOrNull()

    @Transactional
    open fun get(id: Long): Entity =
        getOrNull(id) ?: throw HttpException.NotFound("$name with id '$id' not found.")

    @Transactional
    open fun getAll(
        sort: Sort = Sort.unsorted(),
        rsql: String? = null,
        specification: Specification<Entity> = Specification.unrestricted()
    ): Collection<Entity> = try {
        val rsqlSpecification = if (rsql != null) {
            RSQLJPASupport.toSpecification<Entity>(rsql)
        } else Specification.unrestricted()

        val finalSpecification = rsqlSpecification.and(specification)
        repository.findAll(finalSpecification, sort)
    } catch (exception: RSQLParserException) {
        throw HttpException.BadRequest(exception.message!!)
    } catch (exception: ConversionException) {
        throw HttpException.BadRequest(exception.message!!)
    }

    @Transactional
    open fun getPage(
        pageable: Pageable,
        rsql: String? = null,
        specification: Specification<Entity> = Specification.unrestricted()
    ): Page<Entity> = try {
        val rsqlSpecification = if (rsql != null) {
            RSQLJPASupport.toSpecification<Entity>(rsql)
        } else Specification.unrestricted()

        val finalSpecification = rsqlSpecification.and(specification)
        repository.findAll(finalSpecification, pageable)
    } catch (exception: RSQLParserException) {
        throw HttpException.BadRequest(exception.message!!)
    } catch (exception: ConversionException) {
        throw HttpException.BadRequest(exception.message!!)
    }

    @Transactional
    open fun patch(id: Long, request: PatchRequest): Entity {
        val entity = get(id)
        val updated = mapper.patch(entity, request)
        return repository.save(updated)
    }

    @Transactional
    open fun delete(id: Long) = repository.delete(get(id))

    @Transactional
    open fun deleteAll(iterable: Iterable<Entity>) = repository.deleteAll(iterable)

    @Transactional
    open fun count() = repository.count()

}