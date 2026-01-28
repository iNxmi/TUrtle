package de.csw.turtle.api.controller

import de.csw.turtle.api.SimpleUserDetails
import de.csw.turtle.api.entity.CRUDEntity
import de.csw.turtle.api.service.CRUDService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable

interface DeleteController<Entity : CRUDEntity> {

    @DeleteMapping("/{id}")
    fun delete(
        @AuthenticationPrincipal userDetails: SimpleUserDetails?,
        @PathVariable id: Long
    ): ResponseEntity<Void>

}