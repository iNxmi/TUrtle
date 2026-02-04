package de.csw.turtle.api.controller.api

import de.csw.turtle.api.Permission
import de.csw.turtle.api.controller.CreateController
import de.csw.turtle.api.controller.DeleteController
import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.controller.PatchController
import de.csw.turtle.api.dto.create.CreateDeviceCategoryRequest
import de.csw.turtle.api.dto.get.GetDeviceCategoryResponse
import de.csw.turtle.api.dto.patch.PatchDeviceCategoryRequest
import de.csw.turtle.api.entity.DeviceCategoryEntity
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.mapper.DeviceCategoryMapper
import de.csw.turtle.api.service.DeviceCategoryService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/device-categories")
class DeviceCategoryController(
    private val deviceCategoryService: DeviceCategoryService,
    private val deviceCategoryMapper: DeviceCategoryMapper
) :
    CreateController<DeviceCategoryEntity, CreateDeviceCategoryRequest, GetDeviceCategoryResponse>,
    GetController<DeviceCategoryEntity, Long, GetDeviceCategoryResponse>,
    PatchController<DeviceCategoryEntity, PatchDeviceCategoryRequest, GetDeviceCategoryResponse>,
    DeleteController<DeviceCategoryEntity> {

    override fun create(
        user: UserEntity?,
        request: CreateDeviceCategoryRequest
    ): ResponseEntity<GetDeviceCategoryResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_DEVICE_CATEGORIES))
            throw HttpException.Forbidden()

        val entity = deviceCategoryService.create(request)
        val location = URI.create("/api/device-categories/${entity.id}")
        val dto = deviceCategoryMapper.get(entity)
        return ResponseEntity.created(location).body(dto)
    }

    override fun get(
        user: UserEntity?,
        variable: Long
    ): ResponseEntity<GetDeviceCategoryResponse> {
        val entity = deviceCategoryService.get(variable)
        val dto = deviceCategoryMapper.get(entity)
        return ResponseEntity.ok(dto)
    }

    override fun getCollection(
        user: UserEntity?,
        rsql: String?,
        pageNumber: Int?,
        pageSize: Int,
        sortProperty: String?,
        sortDirection: Sort.Direction
    ): ResponseEntity<Any> {
        val sort = sortProperty?.let {
            Sort.by(sortDirection, sortProperty)
        } ?: Sort.unsorted()

        if (pageNumber != null) {
            val pageable = PageRequest.of(pageNumber, pageSize, sort)
            val page = deviceCategoryService.getPage(rsql = rsql, pageable = pageable)
            val dto = page.map { deviceCategoryMapper.get(it) }
            return ResponseEntity.ok(dto)
        }

        val collection = deviceCategoryService.getAll(rsql = rsql, sort = sort).toMutableSet()
        val dto = collection.map { deviceCategoryMapper.get(it) }
        return ResponseEntity.ok(dto)
    }

    override fun patch(
        user: UserEntity?,
        id: Long,
        request: PatchDeviceCategoryRequest
    ): ResponseEntity<GetDeviceCategoryResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_DEVICE_CATEGORIES))
            throw HttpException.Forbidden()

        val entity = deviceCategoryService.patch(id, request)
        val dto = deviceCategoryMapper.get(entity)
        return ResponseEntity.ok(dto)
    }

    override fun delete(
        user: UserEntity?,
        id: Long
    ): ResponseEntity<Void> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_DEVICE_CATEGORIES))
            throw HttpException.Forbidden()

        deviceCategoryService.delete(id)
        return ResponseEntity.noContent().build()
    }

}