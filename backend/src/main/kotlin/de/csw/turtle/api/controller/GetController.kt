package de.csw.turtle.api.controller

import de.csw.turtle.api.SimpleUserDetails
import de.csw.turtle.api.dto.get.GetResponse
import de.csw.turtle.api.entity.CRUDEntity
import de.csw.turtle.api.mapper.CRUDMapper
import de.csw.turtle.api.service.CRUDService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Direction
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

interface GetController<Entity : CRUDEntity, Response : GetResponse> {

    @GetMapping("/{id}")
    fun get(
        @AuthenticationPrincipal userDetails: SimpleUserDetails?,
        @PathVariable id: Long
    ): ResponseEntity<Response>

    @GetMapping
    fun getCollection(
        @AuthenticationPrincipal userDetails: SimpleUserDetails?,

        @RequestParam rsql: String? = null,

        @RequestParam pageNumber: Int? = null,
        @RequestParam pageSize: Int = 25,

        @RequestParam sortProperty: String? = null,
        @RequestParam sortDirection: Direction = Direction.DESC
    ): ResponseEntity<Any>

}