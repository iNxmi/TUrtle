package de.csw.turtle.api.controller

import de.csw.turtle.api.dto.create.CreateDeviceRequest
import de.csw.turtle.api.dto.get.GetDeviceResponse
import de.csw.turtle.api.dto.patch.PatchDeviceRequest
import de.csw.turtle.api.exception.exceptions.device.DeviceNotFoundException
import de.csw.turtle.api.security.Permission.*
import de.csw.turtle.api.security.RequiresPermission
import de.csw.turtle.api.service.DeviceService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort.Direction
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api/devices")
class DeviceController(
    private val deviceService: DeviceService,
) {

    @RequiresPermission(API_DEVICES_CREATE)
    @PostMapping
    fun create(
        @RequestBody request: CreateDeviceRequest
    ): ResponseEntity<GetDeviceResponse> {
        val entity = deviceService.create(request)
        return ResponseEntity
            .created(URI.create("/api/devices/${entity.id}"))
            .body(GetDeviceResponse(entity))
    }

    @RequiresPermission(API_DEVICES_GET)
    @GetMapping
    fun getPaginated(
        @RequestParam(name = "page", required = false) pageNumber: Int = 0,
        @RequestParam(name = "size", required = false) pageSize: Int = 20,
        @RequestParam(name = "sort", required = false) sort: Array<String> = emptyArray(),
        @RequestParam(name = "direction", required = false) direction: Direction = Direction.DESC
    ): ResponseEntity<Page<GetDeviceResponse>> {
        val pageRequest = if (sort.isEmpty()) {
            PageRequest.of(pageNumber, pageSize)
        } else {
            PageRequest.of(pageNumber, pageSize, direction, *sort)
        }
        val page = deviceService.getPaginated(pageRequest)
        return ResponseEntity.ok(page.map { GetDeviceResponse(it) })
    }

    @RequiresPermission(API_DEVICES_GET)
    @GetMapping("/{id}")
    fun get(
        @PathVariable id: Long
    ): ResponseEntity<GetDeviceResponse> {
        val entity = deviceService.get(id)
            ?: throw DeviceNotFoundException(id)

        return ResponseEntity.ok(GetDeviceResponse(entity))
    }

    @RequiresPermission(API_DEVICES_PATCH)
    @PatchMapping("/{id}")
    fun patch(
        @PathVariable id: Long,
        @RequestBody request: PatchDeviceRequest
    ): ResponseEntity<GetDeviceResponse> {
        val entity = deviceService.update(id, request)
        return ResponseEntity.ok(GetDeviceResponse(entity))
    }

    @RequiresPermission(API_DEVICES_DELETE)
    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: Long
    ): ResponseEntity<Void> {
        deviceService.delete(id)
        return ResponseEntity.noContent().build()
    }

}