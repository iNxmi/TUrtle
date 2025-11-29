package de.csw.turtle.api.controller.crud

import de.csw.turtle.api.Permission
import de.csw.turtle.api.entity.CRUDEntity
import de.csw.turtle.api.service.CRUDService
import de.csw.turtle.api.service.SecurityService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable

interface DeleteController<Entity: CRUDEntity> {

    val service: CRUDService<Entity, *, *, *, *, *>
    val securityService: SecurityService
    val deletePermission: Permission

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        securityService.required(deletePermission)

        service.delete(id)
        return ResponseEntity.noContent().build()
    }

}