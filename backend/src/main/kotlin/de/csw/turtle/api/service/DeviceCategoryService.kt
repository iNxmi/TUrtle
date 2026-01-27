package de.csw.turtle.api.service

import de.csw.turtle.api.dto.create.CreateDeviceCategoryRequest
import de.csw.turtle.api.dto.get.GetDeviceCategoryResponse
import de.csw.turtle.api.dto.patch.PatchDeviceCategoryRequest
import de.csw.turtle.api.entity.DeviceCategoryEntity
import de.csw.turtle.api.exception.ConflictException
import de.csw.turtle.api.exception.NotFoundException
import de.csw.turtle.api.mapper.DeviceCategoryMapper
import de.csw.turtle.api.repository.DeviceCategoryRepository
import org.springframework.stereotype.Service

@Service
class DeviceCategoryService(
    override val repository: DeviceCategoryRepository,
    override val mapper: DeviceCategoryMapper
) : CRUDService<DeviceCategoryEntity, CreateDeviceCategoryRequest, GetDeviceCategoryResponse, PatchDeviceCategoryRequest>(
    "DeviceCategory"
) {
    fun getByNameOrNull(name: String): DeviceCategoryEntity? = repository.findByName(name)
    fun getByName(name: String) = repository.findByName(name) ?: throw NotFoundException(name)

    override fun create(request: CreateDeviceCategoryRequest): DeviceCategoryEntity {
        if(getByNameOrNull(request.name) != null)
            throw ConflictException("DeviceCategory with name '${request.name}' already exists.")

        return super.create(request)
    }

    override fun patch(id: Long, request: PatchDeviceCategoryRequest): DeviceCategoryEntity {
        if(request.name != null)
            if(getByNameOrNull(request.name) != null)
                throw ConflictException("DeviceCategory with name '${request.name}' already exists.")

        return super.patch(id, request)
    }

}