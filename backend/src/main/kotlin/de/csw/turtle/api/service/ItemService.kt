package de.csw.turtle.api.service

import de.csw.turtle.api.dto.create.CreateItemRequest
import de.csw.turtle.api.dto.get.GetItemResponse
import de.csw.turtle.api.dto.patch.PatchItemRequest
import de.csw.turtle.api.entity.ItemEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.mapper.ItemMapper
import de.csw.turtle.api.repository.DeviceRepository
import org.springframework.stereotype.Service

@Service
class ItemService(
    override val repository: DeviceRepository,
    override val mapper: ItemMapper
) : CRUDService<ItemEntity, CreateItemRequest, GetItemResponse, PatchItemRequest>("Item"){

    private val maxNameLength = 64
    private val maxDescriptionLength = 256

    fun getByNameOrNull(name: String): ItemEntity? = repository.findByName(name)

    override fun create(request: CreateItemRequest): ItemEntity {
        if(request.name.isBlank() || request.name.length > maxNameLength)
            throw HttpException.BadRequest("Name cannot be blank and cannot exceed $maxNameLength characters.")
        else if(getByNameOrNull(request.name) != null)
            throw HttpException.Conflict("Name '${request.name}' already exists.")

        if(request.description.length > maxDescriptionLength)
            throw HttpException.BadRequest("Description cannot exceed $maxDescriptionLength characters.")

        return super.create(request)
    }

    override fun patch(id: Long, request: PatchItemRequest): ItemEntity {
        if(request.name != null)
            if(request.name.isBlank() || request.name.length > maxNameLength)
                throw HttpException.BadRequest("Name cannot be blank and cannot exceed $maxNameLength characters.")
            else if(getByNameOrNull(request.name) != null)
                throw HttpException.Conflict("Name '${request.name}' already exists.")

        if(request.description != null)
            if(request.description.length > maxDescriptionLength)
                throw HttpException.BadRequest("Description cannot exceed $maxDescriptionLength characters.")

        return super.patch(id, request)
    }
}