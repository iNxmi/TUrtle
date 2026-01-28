package de.csw.turtle.api.controller.api

import de.csw.turtle.api.Permission
import de.csw.turtle.api.controller.CreateController
import de.csw.turtle.api.controller.DeleteController
import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.controller.PatchController
import de.csw.turtle.api.dto.create.CreateDeviceBookingRequest
import de.csw.turtle.api.dto.get.GetDeviceBookingResponse
import de.csw.turtle.api.dto.patch.PatchDeviceBookingRequest
import de.csw.turtle.api.entity.DeviceBookingEntity
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.ForbiddenException
import de.csw.turtle.api.exception.UnauthorizedException
import de.csw.turtle.api.mapper.DeviceBookingMapper
import de.csw.turtle.api.service.DeviceBookingService
import de.csw.turtle.api.service.UserService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/device-bookings")
class DeviceBookingController(
    private val deviceBookingService: DeviceBookingService,
    private val deviceBookingMapper: DeviceBookingMapper
) : CreateController<DeviceBookingEntity, CreateDeviceBookingRequest, GetDeviceBookingResponse>,
    GetController<DeviceBookingEntity, GetDeviceBookingResponse>,
    PatchController<DeviceBookingEntity, PatchDeviceBookingRequest, GetDeviceBookingResponse>,
    DeleteController<DeviceBookingEntity> {

    override fun create(
        user: UserEntity?,
        request: CreateDeviceBookingRequest
    ): ResponseEntity<GetDeviceBookingResponse> {
        if (user == null)
            throw UnauthorizedException()

        val sanitized = if (!user.hasPermission(Permission.MANAGE_DEVICE_BOOKINGS)) {
            request.copy(
                userId = user.id,
                status = DeviceBookingEntity.Status.RESERVED
            )
        } else request

        val entity = deviceBookingService.create(sanitized)
        val location = URI.create("/api/device-bookings/${entity.id}")
        val response = deviceBookingMapper.get(entity)
        return ResponseEntity.created(location).body(response)
    }

    override fun get(
        user: UserEntity?,
        id: Long
    ): ResponseEntity<GetDeviceBookingResponse> {
        if (user == null)
            throw UnauthorizedException()

        val entity = deviceBookingService.get(id)

        if (!user.hasPermission(Permission.MANAGE_DEVICE_BOOKINGS))
            if (entity.user != user)
                throw ForbiddenException()

        return ResponseEntity.ok(deviceBookingMapper.get(entity))
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
            throw UnauthorizedException()

        val sort = sortProperty?.let {
            Sort.by(sortDirection, sortProperty)
        } ?: Sort.unsorted()

        if (pageNumber != null) {
            val pageable = PageRequest.of(pageNumber, pageSize, sort)
            val page = deviceBookingService.getPage(rsql = rsql, pageable = pageable)

            if (!user.hasPermission(Permission.MANAGE_DEVICE_BOOKINGS))
                page.removeAll { it.user != user }

            val result = page.map { deviceBookingMapper.get(it) }
            return ResponseEntity.ok(result)
        }

        val collection = deviceBookingService.getAll(rsql = rsql, sort = sort).toMutableSet()

        if (!user.hasPermission(Permission.MANAGE_DEVICE_BOOKINGS))
            collection.removeAll { it.user != user }

        val result = collection.map { deviceBookingMapper.get(it) }
        return ResponseEntity.ok(result)
    }

    override fun patch(
        user: UserEntity?,
        id: Long,
        request: PatchDeviceBookingRequest
    ): ResponseEntity<GetDeviceBookingResponse> {
        if (user == null)
            throw UnauthorizedException()

        val entity = deviceBookingService.get(id)

        val sanitized = if (!user.hasPermission(Permission.MANAGE_DEVICE_BOOKINGS)) {
            if (entity.user != user)
                throw ForbiddenException()

            request.copy(userId = null, status = null)
        } else request

        val updated = deviceBookingService.patch(id, sanitized)
        return ResponseEntity.ok(deviceBookingMapper.get(updated))
    }

    override fun delete(
        user: UserEntity?,
        id: Long
    ): ResponseEntity<Void> {
        if (user == null)
            throw UnauthorizedException()

        val entity = deviceBookingService.get(id)

        if (!user.hasPermission(Permission.MANAGE_DEVICE_BOOKINGS))
            if (entity.user != user)
                throw ForbiddenException()

        deviceBookingService.delete(id)
        return ResponseEntity.noContent().build()
    }

}