package de.csw.turtle.api.controller.api

import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.controller.PatchController
import de.csw.turtle.api.dto.get.GetSystemSettingResponse
import de.csw.turtle.api.dto.patch.PatchSystemSettingRequest
import de.csw.turtle.api.entity.SystemSettingEntity
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/system-settings")
class SystemSettingController : 
    GetController<SystemSettingEntity, GetSystemSettingResponse>,
    PatchController<SystemSettingEntity, PatchSystemSettingRequest, GetSystemSettingResponse> {

    override fun get(
        user: UserEntity?,
        id: Long
    ): ResponseEntity<GetSystemSettingResponse> {
        TODO("Not yet implemented")
    }

    override fun getCollection(
        user: UserEntity?,
        rsql: String?,
        pageNumber: Int?,
        pageSize: Int,
        sortProperty: String?,
        sortDirection: Sort.Direction
    ): ResponseEntity<Any> {
        TODO("Not yet implemented")
    }

    override fun patch(
        user: UserEntity?,
        id: Long,
        request: PatchSystemSettingRequest
    ): ResponseEntity<GetSystemSettingResponse> {
        TODO("Not yet implemented")
    }

}