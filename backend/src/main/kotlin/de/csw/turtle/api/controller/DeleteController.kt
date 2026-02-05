package de.csw.turtle.api.controller

import de.csw.turtle.api.entity.CRUDEntity
import de.csw.turtle.api.entity.UserEntity
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable

interface DeleteController<Entity : CRUDEntity> {

    @DeleteMapping("/{id}")
    fun delete(
        @AuthenticationPrincipal user: UserEntity?,
        @PathVariable id: Long,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<Void>

}