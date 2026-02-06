package de.csw.turtle.api.service

import de.csw.turtle.api.entity.RoleEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.repository.RoleRepository
import org.springframework.stereotype.Service

@Service
class RoleService(
    override val repository: RoleRepository
) : CRUDService<RoleEntity>() {

    fun getByName(name: String) = getByNameOrNull(name) ?: throw HttpException.NotFound(name)
    fun getByNameOrNull(name: String) = repository.findByName(name)

//    override fun create(request: CreateRoleRequest): RoleEntity {
//        if(getByNameOrNull(request.name) != null)
//            throw HttpException.Conflict("Role with name '${request.name}' already exists.")
//        return super.create(request)
//    }
//
//    override fun patch(id: Long, request: PatchRoleRequest): RoleEntity {
//        if(request.name != null)
//            if(getByNameOrNull(request.name) != null)
//                throw HttpException.Conflict("Role with name '${request.name}' already exists.")
//        return super.patch(id, request)
//    }
}