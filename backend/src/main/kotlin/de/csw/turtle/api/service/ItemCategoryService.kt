package de.csw.turtle.api.service

import de.csw.turtle.api.entity.ItemCategoryEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.repository.ItemCategoryRepository
import org.springframework.stereotype.Service

@Service
class ItemCategoryService(
    override val repository: ItemCategoryRepository
) : CRUDService<ItemCategoryEntity>() {
    fun getByNameOrNull(name: String): ItemCategoryEntity? = repository.findByName(name)
    fun getByName(name: String) = repository.findByName(name) ?: throw HttpException.NotFound(name)

//    override fun create(request: CreateItemCategoryRequest): ItemCategoryEntity {
//        if(getByNameOrNull(request.name) != null)
//            throw HttpException.Conflict("DeviceCategory with name '${request.name}' already exists.")
//
//        return super.create(request)
//    }
//
//    override fun patch(id: Long, request: PatchItemCategoryRequest): ItemCategoryEntity {
//        if(request.name != null)
//            if(getByNameOrNull(request.name) != null)
//                throw HttpException.Conflict("DeviceCategory with name '${request.name}' already exists.")
//
//        return super.patch(id, request)
//    }

}