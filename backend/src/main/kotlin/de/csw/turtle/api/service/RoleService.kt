package de.csw.turtle.api.service

import de.csw.turtle.api.Permission
import de.csw.turtle.api.entity.RoleEntity
import de.csw.turtle.api.entity.RoleEntity.Type
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.repository.RoleRepository
import org.springframework.stereotype.Service

@Service
class RoleService(
    override val repository: RoleRepository
) : CRUDService<RoleEntity>() {

    fun existsByType(type: Type): Boolean = repository.existsByType(type)
    fun getByType(type: Type): RoleEntity? = repository.findByType(type)
    fun getByName(name: String) = getByNameOrNull(name) ?: throw HttpException.NotFound(name)
    fun getByNameOrNull(name: String) = repository.findByName(name)

    fun create(
        name: String,
        permissions: Set<Permission>,
        type: Type?
    ): RoleEntity {
        val entity = RoleEntity(
            name = name,
            permissions = permissions.toMutableSet(),
            type = type
        )

        return repository.save(entity)
    }

    fun patch(
        id: Long,
        name: String? = null,
        permissions: Set<Permission>? = null,
        type: Type? = null
    ): RoleEntity {
        val entity = repository.findById(id).get()

        name?.let { entity.name = it }
        permissions?.let {
            entity.permissions.clear()
            entity.permissions.addAll(permissions)
        }
        type?.let { entity.type = it }

        return repository.save(entity)
    }

}