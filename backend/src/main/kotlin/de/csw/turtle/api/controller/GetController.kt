package de.csw.turtle.api.controller

import de.csw.turtle.api.dto.get.GetResponse
import de.csw.turtle.api.entity.CRUDEntity
import de.csw.turtle.api.entity.UserEntity
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.domain.Sort.Direction
import org.springframework.http.ResponseEntity

interface GetController<Entity : CRUDEntity, Variable : Any, Response : GetResponse> {

    fun get(
        user: UserEntity?,
        variable: Variable,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<Response>

    fun getCollection(
        user: UserEntity?,

        rsql: String? = null,

        pageNumber: Int? = null,
        pageSize: Int = 25,

        sortProperty: String? = null,
        sortDirection: Direction = Direction.DESC,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<Any>

}