package de.csw.turtle.api.service

import de.csw.turtle.api.dto.create.CreateRoleRequest
import de.csw.turtle.api.dto.get.GetRoleResponse
import de.csw.turtle.api.dto.patch.PatchRoleRequest
import de.csw.turtle.api.entity.RoleEntity
import de.csw.turtle.api.mapper.RoleMapper
import de.csw.turtle.api.repository.RoleRepository
import org.springframework.stereotype.Service

@Service
class RoleService(
    override val repository: RoleRepository,
    override val mapper: RoleMapper
) : CRUDService<RoleEntity, CreateRoleRequest, GetRoleResponse, PatchRoleRequest>("Role") {

    fun getByName(name: String) = repository.findByName(name)

}