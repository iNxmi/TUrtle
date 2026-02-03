package de.csw.turtle.api.service

import de.csw.turtle.api.dto.create.CreateEmailTemplateRequest
import de.csw.turtle.api.dto.get.GetEmailTemplateResponse
import de.csw.turtle.api.dto.patch.PatchEmailTemplateRequest
import de.csw.turtle.api.entity.EmailTemplateEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.mapper.EmailTemplateMapper
import de.csw.turtle.api.repository.EmailTemplateRepository
import org.springframework.stereotype.Service

@Service
class EmailTemplateService(
    override val repository: EmailTemplateRepository,
    override val mapper: EmailTemplateMapper
) : CRUDService<EmailTemplateEntity, CreateEmailTemplateRequest, GetEmailTemplateResponse, PatchEmailTemplateRequest>("Email Template") {

    fun getByNameOrNull(name: String): EmailTemplateEntity? = repository.findByName(name)
    fun getByName(name: String): EmailTemplateEntity = repository.findByName(name) ?: throw HttpException.NotFound(name)

    override fun create(request: CreateEmailTemplateRequest): EmailTemplateEntity {
        if(getByNameOrNull(request.name) != null)
            throw HttpException.Conflict("Email template with name '${request.name}' already exists.")

        return super.create(request)
    }

    override fun patch(id: Long, request: PatchEmailTemplateRequest): EmailTemplateEntity {
        if(request.name != null)
            if(getByNameOrNull(request.name) != null)
                throw HttpException.Conflict("Email template with name '${request.name}' already exists.")

        return super.patch(id, request)
    }

}