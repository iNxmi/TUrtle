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
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.mapper.DeviceBookingMapper
import de.csw.turtle.api.service.DeviceBookingService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
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
            throw HttpException.Unauthorized()

        val sanitized = if (!user.hasPermission(Permission.MANAGE_DEVICE_BOOKINGS)) {
            request.copy(
                userId = user.id,
                status = DeviceBookingEntity.Status.RESERVED
            )
        } else request

        val entity = deviceBookingService.create(sanitized)
        val location = URI.create("/api/device-bookings/${entity.id}")
        val dto = deviceBookingMapper.get(entity)
        return ResponseEntity.created(location).body(dto)
    }

    override fun get(
        user: UserEntity?,
        id: Long
    ): ResponseEntity<GetDeviceBookingResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        val entity = deviceBookingService.get(id)

        if (!user.hasPermission(Permission.MANAGE_DEVICE_BOOKINGS))
            if (entity.user != user)
                throw  HttpException.Forbidden()

        val dto = deviceBookingMapper.get(entity)
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
            throw  HttpException.Unauthorized()

        val sort = sortProperty?.let {
            Sort.by(sortDirection, sortProperty)
        } ?: Sort.unsorted()

        val specification: Specification<DeviceBookingEntity> =
            if (user.hasPermission(Permission.MANAGE_DEVICE_BOOKINGS)) {
                Specification.unrestricted()
            } else Specification { root, _, builder ->
                builder.equal(root.get<UserEntity>("user"), user)
            }

        if (pageNumber != null) {
            val pageable = PageRequest.of(pageNumber, pageSize, sort)
            val page = deviceBookingService.getPage(rsql = rsql, pageable = pageable, specification = specification)
            val dto = page.map { deviceBookingMapper.get(it) }
            return ResponseEntity.ok(dto)
        }

        val collection =
            deviceBookingService.getAll(rsql = rsql, sort = sort, specification = specification).toMutableSet()
        val dto = collection.map { deviceBookingMapper.get(it) }
        return ResponseEntity.ok(dto)
    }

    override fun patch(
        user: UserEntity?,
        id: Long,
        request: PatchDeviceBookingRequest
    ): ResponseEntity<GetDeviceBookingResponse> {
        if (user == null)
            throw  HttpException.Unauthorized()

        val entity = deviceBookingService.get(id)

        val sanitized = if (!user.hasPermission(Permission.MANAGE_DEVICE_BOOKINGS)) {
            if (entity.user != user)
                throw  HttpException.Forbidden()

            request.copy(userId = null, status = null)
        } else request

        val updated = deviceBookingService.patch(id, sanitized)
        val dto = deviceBookingMapper.get(updated)
        return ResponseEntity.ok(dto)
    }

    override fun delete(
        user: UserEntity?,
        id: Long
    ): ResponseEntity<Void> {
        if (user == null)
            throw HttpException.Unauthorized()

        val entity = deviceBookingService.get(id)

        if (!user.hasPermission(Permission.MANAGE_DEVICE_BOOKINGS))
            if (entity.user != user)
                throw HttpException.Forbidden()

        deviceBookingService.delete(id)
        return ResponseEntity.noContent().build()
    }

}