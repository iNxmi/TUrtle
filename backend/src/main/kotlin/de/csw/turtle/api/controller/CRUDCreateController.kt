package de.csw.turtle.api.controller

import de.csw.turtle.api.Permission
import de.csw.turtle.api.dto.create.CRUDCreateRequest
import de.csw.turtle.api.dto.get.CRUDGetResponse
import de.csw.turtle.api.entity.CRUDEntity
import de.csw.turtle.api.mapper.CRUDMapper
import de.csw.turtle.api.service.CRUDService
import de.csw.turtle.api.service.PermissionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import java.net.URI

interface CRUDCreateController<
        Entity : CRUDEntity,
        CreateRequest : CRUDCreateRequest,
        GetResponse : CRUDGetResponse
        > {

    val endpoint: String
    val service: CRUDService<Entity, CreateRequest, GetResponse, *>
    val mapper: CRUDMapper<Entity, CreateRequest, GetResponse, *>
    val permissionService: PermissionService
    val permissionCreate: Permission?

    @PostMapping
    fun create(@RequestBody request: CreateRequest): ResponseEntity<GetResponse> {
        if (permissionCreate != null)
            permissionService.check(permissionCreate!!)

        val entity = service.create(request)
        val response = mapper.get(entity)
        val location = URI.create("$endpoint/${entity.id}")
        return ResponseEntity.created(location).body(response)
    }

}