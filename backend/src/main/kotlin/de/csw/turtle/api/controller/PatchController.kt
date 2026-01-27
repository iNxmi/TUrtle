package de.csw.turtle.api.controller

import de.csw.turtle.api.dto.get.GetResponse
import de.csw.turtle.api.dto.patch.PatchRequest
import de.csw.turtle.api.entity.CRUDEntity
import de.csw.turtle.api.mapper.CRUDMapper
import de.csw.turtle.api.service.CRUDService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

interface PatchController<Entity : CRUDEntity, Request : PatchRequest, Response : GetResponse> {

    val service: CRUDService<Entity, *, Response, Request>
    val mapper: CRUDMapper<Entity, *, Response, Request>

    @PatchMapping("/{id}")
    fun patch(@PathVariable id: Long, @RequestBody request: Request): ResponseEntity<Response> {
        val entity = service.patch(id, request)
        return ResponseEntity.ok(mapper.get(entity))
    }

}