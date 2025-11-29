package de.csw.turtle.api.controller.crud

import de.csw.turtle.api.Permission
import de.csw.turtle.api.dto.get.CRUDGetResponse
import de.csw.turtle.api.entity.CRUDEntity
import de.csw.turtle.api.mapper.CRUDMapper
import de.csw.turtle.api.service.CRUDService
import de.csw.turtle.api.service.SecurityService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Sort.Direction
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

interface GetController<
        Entity : CRUDEntity,
        GetResponse : CRUDGetResponse
        > {

    val service: CRUDService<Entity, *, GetResponse, *, *, *>
    val mapper: CRUDMapper<Entity, *, GetResponse, *>
    val securityService: SecurityService
    val getPermission: Permission

    @GetMapping("/one/{id}")
    fun get(@PathVariable id: Long): ResponseEntity<GetResponse> {
        securityService.required(getPermission)

        val entity = service.get(id)
        return ResponseEntity.ok(mapper.get(entity))
    }

    @GetMapping("/all")
    fun getAll(): ResponseEntity<Collection<GetResponse>> {
        securityService.required(getPermission)

        val entities = service.getAll()
        return ResponseEntity.ok(entities.map { mapper.get(it) })
    }

    @GetMapping("/page")
    fun getPage(
        @RequestParam(name = "page", required = false) page: Int = 0,
        @RequestParam(name = "size", required = false) size: Int = 20,
        @RequestParam(name = "sort", required = false) sort: Array<String> = emptyArray(),
        @RequestParam(name = "direction", required = false) direction: Direction = Direction.DESC
    ): ResponseEntity<Page<GetResponse>> {
        securityService.required(getPermission)

        val result = service.getPage(page, size, sort, direction)
        return ResponseEntity.ok(result.map { mapper.get(it) })
    }

}