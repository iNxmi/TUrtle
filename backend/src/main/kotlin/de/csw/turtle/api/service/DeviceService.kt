package de.csw.turtle.api.service

import de.csw.turtle.api.dto.create.CreateDeviceRequest
import de.csw.turtle.api.dto.get.GetDeviceResponse
import de.csw.turtle.api.dto.patch.PatchDeviceRequest
import de.csw.turtle.api.entity.DeviceEntity
import de.csw.turtle.api.exception.BadRequestException
import de.csw.turtle.api.exception.ConflictException
import de.csw.turtle.api.mapper.DeviceMapper
import de.csw.turtle.api.repository.DeviceRepository
import org.springframework.stereotype.Service

@Service
class DeviceService(
    override val repository: DeviceRepository,
    override val mapper: DeviceMapper
) : CRUDService<DeviceEntity, CreateDeviceRequest, GetDeviceResponse, PatchDeviceRequest>("Device"){

    private val maxNameLength = 64
    private val maxDescriptionLength = 256

    fun getByNameOrNull(name: String): DeviceEntity? = repository.findByName(name)

    override fun create(request: CreateDeviceRequest): DeviceEntity {
        if(request.name.isBlank() || request.name.length > maxNameLength)
            throw BadRequestException("Name cannot be blank and cannot exceed $maxNameLength characters.")
        else if(getByNameOrNull(request.name) != null)
            throw ConflictException("Name '${request.name}' already exists.")

        if(request.description.length > maxDescriptionLength)
            throw BadRequestException("Description cannot exceed $maxDescriptionLength characters.")

        return super.create(request)
    }

    override fun patch(id: Long, request: PatchDeviceRequest): DeviceEntity {
        if(request.name != null)
            if(request.name.isBlank() || request.name.length > maxNameLength)
                throw BadRequestException("Name cannot be blank and cannot exceed $maxNameLength characters.")
            else if(getByNameOrNull(request.name) != null)
                throw ConflictException("Name '${request.name}' already exists.")

        if(request.description != null)
            if(request.description.length > maxDescriptionLength)
                throw BadRequestException("Description cannot exceed $maxDescriptionLength characters.")

        return super.patch(id, request)
    }
}