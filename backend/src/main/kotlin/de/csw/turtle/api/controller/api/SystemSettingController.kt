package de.csw.turtle.api.controller.api

import de.csw.turtle.api.SimpleUserDetails
import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.controller.PatchController
import de.csw.turtle.api.dto.get.GetSystemSettingResponse
import de.csw.turtle.api.dto.patch.PatchSystemSettingRequest
import de.csw.turtle.api.entity.SystemSettingEntity
import de.csw.turtle.api.mapper.SystemSettingMapper
import de.csw.turtle.api.service.SystemSettingService
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/system-settings")
class SystemSettingController
    : GetController<SystemSettingEntity, GetSystemSettingResponse>,
    PatchController<SystemSettingEntity, PatchSystemSettingRequest, GetSystemSettingResponse> {

    override fun get(
        userDetails: SimpleUserDetails?,
        id: Long
    ): ResponseEntity<GetSystemSettingResponse> {
        TODO("Not yet implemented")
    }

    override fun getCollection(
        userDetails: SimpleUserDetails?,
        rsql: String?,
        pageNumber: Int?,
        pageSize: Int,
        sortProperty: String?,
        sortDirection: Sort.Direction
    ): ResponseEntity<Any> {
        TODO("Not yet implemented")
    }

    override fun patch(
        userDetails: SimpleUserDetails?,
        id: Long,
        request: PatchSystemSettingRequest
    ): ResponseEntity<GetSystemSettingResponse> {
        TODO("Not yet implemented")
    }

}