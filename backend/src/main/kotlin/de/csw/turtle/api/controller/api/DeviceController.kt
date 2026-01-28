package de.csw.turtle.api.controller.api

import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.controller.CreateController
import de.csw.turtle.api.controller.DeleteController
import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.controller.PatchController
import de.csw.turtle.api.dto.create.CreateDeviceRequest
import de.csw.turtle.api.dto.get.GetDeviceResponse
import de.csw.turtle.api.dto.patch.PatchDeviceRequest
import de.csw.turtle.api.entity.DeviceEntity
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/devices")
class DeviceController :
    CreateController<DeviceEntity, CreateDeviceRequest, GetDeviceResponse>,
    GetController<DeviceEntity, GetDeviceResponse>,
    PatchController<DeviceEntity, PatchDeviceRequest, GetDeviceResponse>,
    DeleteController<DeviceEntity> {

    override fun create(
        user: UserEntity?,
        request: CreateDeviceRequest
    ): ResponseEntity<GetDeviceResponse> {
        TODO("Not yet implemented")
    }

    override fun get(
        user: UserEntity?,
        id: Long
    ): ResponseEntity<GetDeviceResponse> {
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
        request: PatchDeviceRequest
    ): ResponseEntity<GetDeviceResponse> {
        TODO("Not yet implemented")
    }

    override fun delete(
        user: UserEntity?,
        id: Long
    ): ResponseEntity<Void> {
        TODO("Not yet implemented")
    }

}