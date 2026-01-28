package de.csw.turtle.api.controller

import de.csw.turtle.api.SimpleUserDetails
import de.csw.turtle.api.dto.create.CreateRequest
import de.csw.turtle.api.dto.get.GetResponse
import de.csw.turtle.api.entity.CRUDEntity
import de.csw.turtle.api.mapper.CRUDMapper
import de.csw.turtle.api.service.CRUDService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import java.net.URI

interface CreateController<Entity : CRUDEntity, Request : CreateRequest, Response : GetResponse> {

    @PostMapping
    fun create(
        @AuthenticationPrincipal userDetails: SimpleUserDetails?,
        @RequestBody request: Request
    ): ResponseEntity<Response>

}