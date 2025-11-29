package de.csw.turtle.api.repository

import de.csw.turtle.api.entity.RoleEntity

interface RoleRepository: CRUDRepository<RoleEntity> {

    fun findByName(name: String): RoleEntity?

}