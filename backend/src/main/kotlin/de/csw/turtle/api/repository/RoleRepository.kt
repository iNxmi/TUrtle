package de.csw.turtle.api.repository

import de.csw.turtle.api.entity.RoleEntity
import de.csw.turtle.api.entity.RoleEntity.Type

interface RoleRepository : CRUDRepository<RoleEntity> {

    fun existsByType(type: Type): Boolean
    fun findByName(name: String): RoleEntity?
    fun findByType(type: Type): RoleEntity?

}