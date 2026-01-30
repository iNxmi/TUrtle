package de.csw.turtle.api.controller.api

import de.csw.turtle.api.Permission
import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.controller.PatchController
import de.csw.turtle.api.dto.get.GetSystemSettingResponse
import de.csw.turtle.api.dto.patch.PatchSystemSettingRequest
import de.csw.turtle.api.entity.SystemSettingEntity
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.mapper.SystemSettingMapper
import de.csw.turtle.api.service.SystemSettingService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/system-settings")
class SystemSettingController(
    private val systemSettingService: SystemSettingService,
    private val systemSettingMapper: SystemSettingMapper
) : GetController<SystemSettingEntity, GetSystemSettingResponse>,
    PatchController<SystemSettingEntity, PatchSystemSettingRequest, GetSystemSettingResponse> {

    override fun get(
        user: UserEntity?,
        id: Long
    ): ResponseEntity<GetSystemSettingResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_SYSTEM_SETTINGS))
            throw HttpException.Forbidden()

        val entity = systemSettingService.get(id)
        val dto = systemSettingMapper.get(entity)
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
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_SYSTEM_SETTINGS))
            throw HttpException.Forbidden()

        val sort = sortProperty?.let {
            Sort.by(sortDirection, sortProperty)
        } ?: Sort.unsorted()

        if (pageNumber != null) {
            val pageable = PageRequest.of(pageNumber, pageSize, sort)
            val page = systemSettingService.getPage(rsql = rsql, pageable = pageable)
            val dto = page.map { systemSettingMapper.get(it) }
            return ResponseEntity.ok(dto)
        }

        val collection = systemSettingService.getAll(rsql = rsql, sort = sort).toMutableSet()
        val dto = collection.map { systemSettingMapper.get(it) }
        return ResponseEntity.ok(dto)
    }

    override fun patch(
        user: UserEntity?,
        id: Long,
        request: PatchSystemSettingRequest
    ): ResponseEntity<GetSystemSettingResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_SYSTEM_SETTINGS))
            throw HttpException.Forbidden()

        val updated = systemSettingService.patch(id, request)
        val dto = systemSettingMapper.get(updated)
        return ResponseEntity.ok(dto)
    }

}