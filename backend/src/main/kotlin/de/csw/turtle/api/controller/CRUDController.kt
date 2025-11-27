package de.csw.turtle.api.controller

import de.csw.turtle.api.dto.create.CRUDCreateRequest
import de.csw.turtle.api.dto.get.CRUDGetResponse
import de.csw.turtle.api.dto.patch.CRUDPatchRequest
import de.csw.turtle.api.entity.CRUDEntity
import de.csw.turtle.api.mapper.CRUDMapper
import de.csw.turtle.api.repository.CRUDRepository
import de.csw.turtle.api.service.CRUDService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Sort.Direction
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

abstract class CRUDController<
        Entity : CRUDEntity,
        CreateRequest : CRUDCreateRequest,
        GetResponse : CRUDGetResponse,
        PatchRequest : CRUDPatchRequest,
        Repository : CRUDRepository<Entity>,
        Mapper : CRUDMapper<Entity, CreateRequest, GetResponse, PatchRequest>,
        Service : CRUDService<Entity, CreateRequest, GetResponse, PatchRequest, Repository, Mapper>
        >(
    protected val prefix: String
) {

    protected abstract val service: Service
    protected abstract val mapper: Mapper

    @PostMapping
    fun create(@RequestBody request: CreateRequest): ResponseEntity<GetResponse> {
        val entity = service.create(request)
        val response = mapper.get(entity)
        val location = URI.create("$prefix/${entity.id}")
        return ResponseEntity.created(location).body(response)
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): ResponseEntity<GetResponse> {
        val entity = service.get(id)
        return ResponseEntity.ok(mapper.get(entity))
    }

    @GetMapping
    fun getPage(
        @RequestParam(name = "page", required = false) page: Int = 0,
        @RequestParam(name = "size", required = false) size: Int = 20,
        @RequestParam(name = "sort", required = false) sort: Array<String> = emptyArray(),
        @RequestParam(name = "direction", required = false) direction: Direction = Direction.DESC
    ): ResponseEntity<Page<GetResponse>> {
        val page = service.getPage(page, size, sort, direction)
        return ResponseEntity.ok(page.map { mapper.get(it) })
    }

    @PatchMapping("/{id}")
    fun patch(@PathVariable id: Long, @RequestBody request: PatchRequest): ResponseEntity<GetResponse> {
        val entity = service.patch(id, request)
        return ResponseEntity.ok(mapper.get(entity))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity.noContent().build()
    }

}