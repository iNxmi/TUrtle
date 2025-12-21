package de.csw.turtle.api.controller

import de.csw.turtle.api.Permission
import de.csw.turtle.api.entity.CRUDEntity
import de.csw.turtle.api.mapper.CRUDMapper
import de.csw.turtle.api.service.CRUDService
import de.csw.turtle.api.service.PermissionService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Sort.Direction
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import java.util.SortedSet

interface GetController<
        Entity : CRUDEntity,
        GetResponse : de.csw.turtle.api.dto.get.GetResponse
        > {

    val service: CRUDService<Entity, *, GetResponse, *>
    val mapper: CRUDMapper<Entity, *, GetResponse, *>
    val permissionService: PermissionService
    val permissionGet: Permission?

    @GetMapping("/one/{id}")
    fun get(@PathVariable id: Long): ResponseEntity<GetResponse> {
        if (permissionGet != null)
            permissionService.check(permissionGet!!)

        val entity = service.get(id)
        return ResponseEntity.ok(mapper.get(entity))
    }

    @GetMapping("/multiple")
    fun get(@RequestParam ids: Set<Long>): ResponseEntity<Collection<GetResponse>> {
        if (permissionGet != null)
            permissionService.check(permissionGet!!)

        val entities = service.getMultiple(ids)
        val result = entities.map { mapper.get(it) }
        return ResponseEntity.ok(result)
    }

    @GetMapping("/all")
    fun getAll(
        @RequestParam(required = false) filter: String? = null
    ): ResponseEntity<Collection<GetResponse>> {
        if (permissionGet != null)
            permissionService.check(permissionGet!!)

        val entities = service.getAll(filter)
        val result = entities.map { mapper.get(it) }
        return ResponseEntity.ok(result)
    }

    @GetMapping("/page")
    fun getPage(
        @RequestParam(name = "page", required = false) page: Int = 0,
        @RequestParam(name = "size", required = false) size: Int = 20,
        @RequestParam(name = "sort", required = false) sort: Array<String> = emptyArray(),
        @RequestParam(name = "direction", required = false) direction: Direction = Direction.DESC,
        @RequestParam(required = false) filter: String? = null
    ): ResponseEntity<Page<GetResponse>> {
        if (permissionGet != null)
            permissionService.check(permissionGet!!)

        val result = service.getPage(page, size, sort, direction, filter)
        return ResponseEntity.ok(result.map { mapper.get(it) })
    }

}