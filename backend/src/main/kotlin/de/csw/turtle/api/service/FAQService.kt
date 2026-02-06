package de.csw.turtle.api.service

import de.csw.turtle.api.entity.FAQEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.repository.FAQRepository
import org.springframework.stereotype.Service

@Service
class FAQService(
    override val repository: FAQRepository
) : CRUDService<FAQEntity>("FAQ"){

    private val maxNameLength = 64
    private val maxTitleLength = 64

    fun getByNameOrNull(name: String) : FAQEntity? = repository.findByName(name)

//    override fun create(request: CreateFAQRequest): FAQEntity {
//        if(request.name.isBlank() || request.name.length > maxNameLength) {
//            throw HttpException.BadRequest("Name is required and cannot exceed $maxNameLength characters.");
//        }
//        else if(getByNameOrNull(request.name) != null) {
//            throw HttpException.Conflict("Name '${request.name}' already exists.");
//        }
//
//        if(request.title.isBlank() || request.title.length > maxTitleLength) {
//            throw HttpException.BadRequest("Title is required and cannot exceed $maxTitleLength characters.");
//        }
//
//        if(request.content.isBlank()){
//            throw HttpException.BadRequest("Content is required.");
//        }
//
//        return super.create(request)
//    }
//
//    override fun patch(id: Long, request: PatchFAQRequest): FAQEntity {
//        if(request.name != null)
//            if(request.name.isBlank() || request.name.length > maxNameLength) {
//                throw HttpException.BadRequest("Name is required and cannot exceed $maxNameLength characters.");
//            }
//            else if(getByNameOrNull(request.name) != null) {
//                throw HttpException.Conflict("Name '${request.name}' already exists.");
//            }
//
//        if(request.title != null)
//            if(request.title.isBlank() || request.title.length > maxTitleLength) {
//                throw HttpException.BadRequest("Title is required and cannot exceed $maxTitleLength characters.");
//            }
//
//        if(request.content != null)
//            if(request.content.isBlank())
//                throw HttpException.BadRequest("Content is required.");
//
//        return super.patch(id, request)
//    }
}