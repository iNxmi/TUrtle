package de.csw.turtle.api.controller

import de.csw.turtle.api.dto.get.GetResponse
import de.csw.turtle.api.dto.patch.PatchRequest
import de.csw.turtle.api.entity.CRUDEntity
import de.csw.turtle.api.entity.UserEntity
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

interface PatchController<Entity : CRUDEntity, Request : PatchRequest, Response : GetResponse> {

    @PatchMapping("/{id}")
    fun patch(
        @AuthenticationPrincipal user: UserEntity?,
        @PathVariable id: Long,
        @RequestBody request: Request,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<Response>

}