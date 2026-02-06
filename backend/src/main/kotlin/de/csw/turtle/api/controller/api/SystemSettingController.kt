package de.csw.turtle.api.controller.api

import de.csw.turtle.api.Permission
import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.controller.PatchController
import de.csw.turtle.api.dto.get.GetSystemSettingResponse
import de.csw.turtle.api.dto.patch.PatchSystemSettingRequest
import de.csw.turtle.api.entity.SystemSettingEntity
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.service.SystemSettingService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/system-settings")
class SystemSettingController(
    private val systemSettingService: SystemSettingService
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

    @GetMapping("/{variable}")
    override fun get(
        @AuthenticationPrincipal   user: UserEntity?,

        @PathVariable variable: String,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetSystemSettingResponse> {
        val id = variable.toLongOrNull()
        val entity = if (id != null) {
            systemSettingService.getById(id)
        } else {
            systemSettingService.getByKey(variable)
        } ?: throw HttpException.NotFound()

        val dto = GetSystemSettingResponse(entity)
        if (entity.visibility == SystemSettingEntity.Visibility.PUBLIC)
            return ResponseEntity.ok(dto)

        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_SYSTEM_SETTINGS))
            throw HttpException.Forbidden()

        return ResponseEntity.ok(dto)
    }

    @GetMapping
    override fun getCollection(
        @AuthenticationPrincipal  user: UserEntity?,

        @RequestParam rsql: String?,
        @RequestParam pageNumber: Int?,
        @RequestParam pageSize: Int,
        @RequestParam sortProperty: String?,
        @RequestParam sortDirection: Sort.Direction,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
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
            val dto = page.map { GetSystemSettingResponse(it) }
            return ResponseEntity.ok(dto)
        }

        val collection =
            systemSettingService.getAll(rsql = rsql, sort = sort, specification = specification).toMutableSet()
        val dto = collection.map { GetSystemSettingResponse(it) }
        return ResponseEntity.ok(dto)
    }

    @PatchMapping("/{id}")
    override fun patch(
        @AuthenticationPrincipal    user: UserEntity?,

        @PathVariable id: Long,
        @RequestBody request: PatchSystemSettingRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetSystemSettingResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_SYSTEM_SETTINGS))
            throw HttpException.Forbidden()

        val entity = systemSettingService.patch(
            id = id,
            key = request.key,
            type = request.type,
            value = request.value,
            visibility = request.visibility
        )

        val dto = GetSystemSettingResponse(entity)
        return ResponseEntity.ok(dto)
    }

}