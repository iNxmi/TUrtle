package de.csw.turtle.api.controller

import de.csw.turtle.api.Permission
import de.csw.turtle.api.dto.get.CRUDGetResponse
import de.csw.turtle.api.dto.patch.CRUDPatchRequest
import de.csw.turtle.api.entity.CRUDEntity
import de.csw.turtle.api.mapper.CRUDMapper
import de.csw.turtle.api.service.CRUDService
import de.csw.turtle.api.service.SecurityService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

interface CRUDPatchController<
        Entity : CRUDEntity,
        PatchRequest : CRUDPatchRequest,
        GetResponse : CRUDGetResponse
        > {

    val service: CRUDService<Entity, *, GetResponse, PatchRequest>
    val mapper: CRUDMapper<Entity, *, GetResponse, PatchRequest>
    val securityService: SecurityService
    val patchPermission: Permission

    @PatchMapping("/{id}")
    fun patch(@PathVariable id: Long, @RequestBody request: PatchRequest): ResponseEntity<GetResponse> {
        securityService.check(patchPermission)

        val entity = service.patch(id, request)
        return ResponseEntity.ok(mapper.get(entity))
    }

}