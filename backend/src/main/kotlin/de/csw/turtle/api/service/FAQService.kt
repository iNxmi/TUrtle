package de.csw.turtle.api.service

import de.csw.turtle.api.dto.create.CreateFAQRequest
import de.csw.turtle.api.dto.get.GetFAQResponse
import de.csw.turtle.api.dto.patch.PatchFAQRequest
import de.csw.turtle.api.entity.FAQEntity
import de.csw.turtle.api.exception.BadRequestException
import de.csw.turtle.api.exception.ConflictException
import de.csw.turtle.api.mapper.FAQMapper
import de.csw.turtle.api.repository.FAQRepository
import org.springframework.stereotype.Service

@Service
class FAQService(
    override val repository: FAQRepository,
    override val mapper: FAQMapper
) : CRUDService<FAQEntity, CreateFAQRequest, GetFAQResponse, PatchFAQRequest>("FAQ"){

    private val maxNameLength = 64
    private val maxTitleLength = 64

    fun getByNameOrNull(name: String) : FAQEntity? = repository.findByName(name)

    override fun create(request: CreateFAQRequest): FAQEntity {
        if(request.name.isBlank() || request.name.length > maxNameLength) {
            throw BadRequestException("Name is required and cannot exceed $maxNameLength characters.");
        }
        else if(getByNameOrNull(request.name) != null) {
            throw ConflictException("Name '${request.name}' already exists.");
        }

        if(request.title.isBlank() || request.title.length > maxTitleLength) {
            throw BadRequestException("Title is required and cannot exceed $maxTitleLength characters.");
        }

        if(request.content.isBlank()){
            throw BadRequestException("Content is required.");
        }

        return super.create(request)
    }

    override fun patch(id: Long, request: PatchFAQRequest): FAQEntity {
        if(request.name != null)
            if(request.name.isBlank() || request.name.length > maxNameLength) {
                throw BadRequestException("Name is required and cannot exceed $maxNameLength characters.");
            }
            else if(getByNameOrNull(request.name) != null) {
                throw ConflictException("Name '${request.name}' already exists.");
            }

        if(request.title != null)
            if(request.title.isBlank() || request.title.length > maxTitleLength) {
                throw BadRequestException("Title is required and cannot exceed $maxTitleLength characters.");
            }

        if(request.content != null)
            if(request.content.isBlank())
                throw BadRequestException("Content is required.");

        return super.patch(id, request)
    }
}