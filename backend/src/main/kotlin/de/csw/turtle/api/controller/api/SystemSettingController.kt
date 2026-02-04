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
import org.springframework.data.jpa.domain.Specification
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/system-settings")
class SystemSettingController(
    private val systemSettingService: SystemSettingService,
    private val systemSettingMapper: SystemSettingMapper
) : GetController<SystemSettingEntity, String, GetSystemSettingResponse>,
    PatchController<SystemSettingEntity, PatchSystemSettingRequest, GetSystemSettingResponse> {

//    @GetMapping("/export")
//    fun export(
//        @AuthenticationPrincipal user: UserEntity?
//    ): ResponseEntity<String> {
//        if (user == null)
//            throw HttpException.Unauthorized()
//
//        if (!user.hasPermission(Permission.MANAGE_SYSTEM_SETTINGS))
//            throw HttpException.Forbidden()
//
//        val collection = systemSettingService.getAll()
//        val map = TreeMap<String, String>()
//        for (entity in collection)
//            map[entity.key] = entity.value
//
//        val builder = StringBuilder()
//        for ((key, value) in map)
//            builder.append("$key=$value\n")
//
//        return ResponseEntity.ok(builder.toString())
//    }
//
//    @GetMapping("/import")
//    fun import(
//        @AuthenticationPrincipal user: UserEntity?
//    ): ResponseEntity<String> {
//        if (user == null)
//            throw HttpException.Unauthorized()
//
//        if (!user.hasPermission(Permission.MANAGE_SYSTEM_SETTINGS))
//            throw HttpException.Forbidden()
//
//        TODO("implement import function")
//    }

    override fun get(
        user: UserEntity?,
        variable: String
    ): ResponseEntity<GetSystemSettingResponse> {
        val id = variable.toLongOrNull()
        val entity = if(id != null) {
            systemSettingService.get(id)
        } else {
            systemSettingService.getByKey(variable)
        }

        val dto = systemSettingMapper.get(entity)
        if (entity.visibility == SystemSettingEntity.Visibility.PUBLIC)
            return ResponseEntity.ok(dto)

        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_SYSTEM_SETTINGS))
            throw HttpException.Forbidden()

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

        val specification = if (user == null || !user.hasPermission(Permission.MANAGE_SYSTEM_SETTINGS)) {
            Specification { root, _, builder ->
                builder.equal(
                    root.get<SystemSettingEntity.Visibility>("visibility"),
                    SystemSettingEntity.Visibility.PUBLIC
                )
            }
        } else Specification.unrestricted<SystemSettingEntity>()

        val sort = sortProperty?.let {
            Sort.by(sortDirection, sortProperty)
        } ?: Sort.unsorted()

        if (pageNumber != null) {
            val pageable = PageRequest.of(pageNumber, pageSize, sort)
            val page = systemSettingService.getPage(rsql = rsql, pageable = pageable, specification = specification)
            val dto = page.map { systemSettingMapper.get(it) }
            return ResponseEntity.ok(dto)
        }

        val collection =
            systemSettingService.getAll(rsql = rsql, sort = sort, specification = specification).toMutableSet()
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