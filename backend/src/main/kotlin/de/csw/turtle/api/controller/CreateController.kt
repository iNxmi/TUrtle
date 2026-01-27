package de.csw.turtle.api.controller

import de.csw.turtle.api.dto.create.CreateRequest
import de.csw.turtle.api.dto.get.GetResponse
import de.csw.turtle.api.entity.CRUDEntity
import de.csw.turtle.api.mapper.CRUDMapper
import de.csw.turtle.api.service.CRUDService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import java.net.URI

interface CreateController<Entity : CRUDEntity, Request : CreateRequest, Response : GetResponse> {

    val endpoint: String
    val service: CRUDService<Entity, Request, Response, *>
    val mapper: CRUDMapper<Entity, Request, Response, *>

    @PostMapping
    fun create(@RequestBody request: Request): ResponseEntity<Response> {
        val entity = service.create(request)
        val response = mapper.get(entity)
        val location = URI.create("$endpoint/${entity.id}")
        return ResponseEntity.created(location).body(response)
    }

}