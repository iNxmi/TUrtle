package de.csw.turtle.api.controller

import de.csw.turtle.api.Permission
import de.csw.turtle.api.dto.get.GetConfigurationResponse
import de.csw.turtle.api.dto.patch.PatchConfigurationRequest
import de.csw.turtle.api.entity.ConfigurationEntity
import de.csw.turtle.api.entity.ConfigurationEntity.Key
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.service.ConfigurationService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/configuration")
class ConfigurationController(
    private val configurationService: ConfigurationService
) : GetController<ConfigurationEntity, String, GetConfigurationResponse>,
    PatchController<ConfigurationEntity, PatchConfigurationRequest, GetConfigurationResponse> {

    @GetMapping("/{variable}")
    override fun get(
        @AuthenticationPrincipal user: UserEntity?,

        @PathVariable variable: String,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetConfigurationResponse> {
        val id = variable.toLongOrNull()
        val entity = if (id != null) {
            configurationService.getById(id)
        } else {
            val key = Key.valueOf(variable.uppercase())
            configurationService.getByKey(key)
        } ?: throw HttpException.NotFound()

        val dto = GetConfigurationResponse(entity)
        if (entity.visibility == ConfigurationEntity.Visibility.PUBLIC)
            return ResponseEntity.ok(dto)

        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_SYSTEM_SETTINGS))
            throw HttpException.Forbidden()

        return ResponseEntity.ok(dto)
    }

    @GetMapping
    override fun getCollection(
        @AuthenticationPrincipal user: UserEntity?,

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
                    root.get<ConfigurationEntity.Visibility>("visibility"),
                    ConfigurationEntity.Visibility.PUBLIC
                )
            }
        } else Specification.unrestricted<ConfigurationEntity>()

        val sort = sortProperty?.let {
            Sort.by(sortDirection, sortProperty)
        } ?: Sort.unsorted()

        if (pageNumber != null) {
            val pageable = PageRequest.of(pageNumber, pageSize, sort)
            val page = configurationService.getPage(rsql = rsql, pageable = pageable, specification = specification)
            val dto = page.map { GetConfigurationResponse(it) }
            return ResponseEntity.ok(dto)
        }

        val collection =
            configurationService.getAll(rsql = rsql, sort = sort, specification = specification).toMutableSet()
        val dto = collection.map { GetConfigurationResponse(it) }
        return ResponseEntity.ok(dto)
    }

    @PatchMapping("/{id}")
    override fun patch(
        @AuthenticationPrincipal user: UserEntity?,

        @PathVariable id: Long,
        @RequestBody request: PatchConfigurationRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetConfigurationResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_SYSTEM_SETTINGS))
            throw HttpException.Forbidden()

        val entity = configurationService.patch(
            id = id,
            key = request.key,
            type = request.type,
            value = request.value,
            visibility = request.visibility
        )

        val dto = GetConfigurationResponse(entity)
        return ResponseEntity.ok(dto)
    }

}