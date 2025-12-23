package de.csw.turtle.api.controller

import de.csw.turtle.api.Permission
import de.csw.turtle.api.dto.get.GetResponse
import de.csw.turtle.api.entity.CRUDEntity
import de.csw.turtle.api.mapper.CRUDMapper
import de.csw.turtle.api.service.CRUDService
import de.csw.turtle.api.service.PermissionService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Direction
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

interface GetController<Entity : CRUDEntity, Response : GetResponse> {

    val service: CRUDService<Entity, *, Response, *>
    val mapper: CRUDMapper<Entity, *, Response, *>
    val permissionService: PermissionService
    val permissionGet: Permission?

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): ResponseEntity<Response> {
        if (permissionGet != null)
            permissionService.check(permissionGet!!)

        val entity = service.get(id)
        return ResponseEntity.ok(mapper.get(entity))
    }

    @GetMapping
    fun getCollection(
        @RequestParam rsql: String? = null,

        @RequestParam pageNumber: Int? = null,
        @RequestParam pageSize: Int = 25,

        @RequestParam sortProperty: String? = null,
        @RequestParam sortDirection: Direction = Direction.DESC
    ): ResponseEntity<Any> {
        if (permissionGet != null)
            permissionService.check(permissionGet!!)

        val sort = sortProperty?.let {
            Sort.by(sortDirection, sortProperty)
        } ?: Sort.unsorted()

        if(pageNumber != null) {
            val pageable = PageRequest.of(pageNumber, pageSize, sort)
            val page = service.getPage(rsql = rsql, pageable = pageable)
            val result = page.map { mapper.get(it) }
            return ResponseEntity.ok(result)
        }

        val entities =  service.getAll(rsql = rsql, sort = sort)
        val result = entities.map { mapper.get(it) }
        return ResponseEntity.ok(result)
    }

}