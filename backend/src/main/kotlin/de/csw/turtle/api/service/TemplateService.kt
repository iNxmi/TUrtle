package de.csw.turtle.api.service

import de.csw.turtle.api.dto.create.CreateTemplateRequest
import de.csw.turtle.api.dto.get.GetTemplateResponse
import de.csw.turtle.api.dto.patch.PatchTemplateRequest
import de.csw.turtle.api.entity.TemplateEntity
import de.csw.turtle.api.exception.BadRequestException
import de.csw.turtle.api.exception.ConflictException
import de.csw.turtle.api.exception.NotFoundException
import de.csw.turtle.api.mapper.TemplateMapper
import de.csw.turtle.api.repository.TemplateRepository
import org.springframework.stereotype.Service

@Service
class TemplateService(
    override val repository: TemplateRepository,
    override val mapper: TemplateMapper
) : CRUDService<TemplateEntity, CreateTemplateRequest, GetTemplateResponse, PatchTemplateRequest>("Template") {

    fun getByNameOrNull(name: String): TemplateEntity? = repository.findByName(name)
    fun getByName(name: String): TemplateEntity = repository.findByName(name) ?: throw NotFoundException(name)

    override fun create(request: CreateTemplateRequest): TemplateEntity {
        if(getByNameOrNull(request.name) != null)
            throw ConflictException("Template with name '${request.name}' already exists.")
        else if(request.name.isBlank())
            throw BadRequestException("Name cannot be blank.")

        return super.create(request)
    }

    override fun patch(id: Long, request: PatchTemplateRequest): TemplateEntity {
        if(request.name != null)
            if(getByNameOrNull(request.name) != null)
                throw ConflictException("Template with name '${request.name}' already exists.")
            else if(request.name.isBlank())
                throw BadRequestException("Name cannot be blank.")

        return super.patch(id, request)
    }

}