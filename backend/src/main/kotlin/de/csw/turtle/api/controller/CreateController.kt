package de.csw.turtle.api.controller

import de.csw.turtle.api.dto.create.CreateRequest
import de.csw.turtle.api.dto.get.GetResponse
import de.csw.turtle.api.entity.CRUDEntity
import de.csw.turtle.api.entity.UserEntity
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity

interface CreateController<Entity : CRUDEntity, Request : CreateRequest, Response : GetResponse> {

    fun create(
        user: UserEntity?,

        request: Request,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<Response>

}