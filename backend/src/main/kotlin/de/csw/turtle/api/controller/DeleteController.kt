package de.csw.turtle.api.controller

import de.csw.turtle.api.entity.CRUDEntity
import de.csw.turtle.api.service.CRUDService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable

interface DeleteController<Entity : CRUDEntity> {

    val service: CRUDService<Entity, *, *, *>

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity.noContent().build()
    }

}