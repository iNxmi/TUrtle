package de.csw.turtle.api.controller

import de.csw.turtle.api.dto.get.GetResponse
import de.csw.turtle.api.entity.CRUDEntity
import de.csw.turtle.api.entity.UserEntity
import org.springframework.data.domain.Sort.Direction
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

interface GetController<Entity : CRUDEntity, Variable : Any, Response : GetResponse> {

    @GetMapping("/{variable}")
    fun get(
        @AuthenticationPrincipal user: UserEntity?,
        @PathVariable variable: Variable
    ): ResponseEntity<Response>

    @GetMapping
    fun getCollection(
        @AuthenticationPrincipal user: UserEntity?,

        @RequestParam rsql: String? = null,

        @RequestParam pageNumber: Int? = null,
        @RequestParam pageSize: Int = 25,

        @RequestParam sortProperty: String? = null,
        @RequestParam sortDirection: Direction = Direction.DESC
    ): ResponseEntity<Any>

}