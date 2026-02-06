package de.csw.turtle.api.service

import de.csw.turtle.api.entity.GeneralTemplateEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.repository.GeneralTemplateRepository
import org.springframework.stereotype.Service

@Service
class GeneralTemplateService(
    override val repository: GeneralTemplateRepository
) : CRUDService<GeneralTemplateEntity>() {

    fun getByNameOrNull(name: String): GeneralTemplateEntity? = repository.findByName(name)
    fun getByName(name: String): GeneralTemplateEntity = repository.findByName(name) ?: throw HttpException.NotFound(name)

//    override fun create(request: CreateGeneralTemplateRequest): GeneralTemplateEntity {
//        if(getByNameOrNull(request.name) != null)
//            throw HttpException.Conflict("General template with name '${request.name}' already exists.")
//
//        return super.create(request)
//    }
//
//    override fun patch(id: Long, request: PatchGeneralTemplateRequest): GeneralTemplateEntity {
//        if(request.name != null)
//            if(getByNameOrNull(request.name) != null)
//                throw HttpException.Conflict("General template with name '${request.name}' already exists.")
//
//        return super.patch(id, request)
//    }

}