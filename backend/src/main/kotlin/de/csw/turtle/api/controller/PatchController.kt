package de.csw.turtle.api.controller

import de.csw.turtle.api.dto.get.GetResponse
import de.csw.turtle.api.dto.patch.PatchRequest
import de.csw.turtle.api.entity.CRUDEntity
import de.csw.turtle.api.entity.UserEntity
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity

interface PatchController<Entity : CRUDEntity, Request : PatchRequest, Response : GetResponse> {

    fun patch(
        user: UserEntity?,

        id: Long,
        request: Request,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<Response>

}