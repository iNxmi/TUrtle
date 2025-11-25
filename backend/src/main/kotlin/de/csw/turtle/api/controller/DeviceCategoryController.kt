package de.csw.turtle.api.controller

import de.csw.turtle.api.dto.create.CreateDeviceCategoryRequest
import de.csw.turtle.api.dto.get.GetDeviceCategoryResponse
import de.csw.turtle.api.dto.get.GetDeviceResponse
import de.csw.turtle.api.dto.patch.PatchDeviceCategoryRequest
import de.csw.turtle.api.dto.patch.PatchLockerRequest
import de.csw.turtle.api.exception.exceptions.device.category.DeviceCategoryNotFoundException
import de.csw.turtle.api.exception.exceptions.locker.LockerNotFoundException
import de.csw.turtle.api.security.Permission.*
import de.csw.turtle.api.security.RequiresPermission
import de.csw.turtle.api.service.DeviceCategoryService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort.Direction
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api/devicecategories")
class DeviceCategoryController(
    private val deviceCategoryService: DeviceCategoryService,
) {

    @RequiresPermission(API_DEVICES_CATEGORIES_CREATE)
    @PostMapping
    fun create(
        @RequestBody request: CreateDeviceCategoryRequest
    ): ResponseEntity<GetDeviceCategoryResponse> {
        val entity = deviceCategoryService.create(request)
        return ResponseEntity
            .created(URI.create("/api/devices/categories/${entity.id}"))
            .body(GetDeviceCategoryResponse(entity))
    }

    @RequiresPermission(API_DEVICES_CATEGORIES_GET)
    @GetMapping
    fun getPaginated(
        @RequestParam(name = "page", required = false) pageNumber: Int = 0,
        @RequestParam(name = "size", required = false) pageSize: Int = 20,
        @RequestParam(name = "sort", required = false) sort: Array<String> = emptyArray(),
        @RequestParam(name = "direction", required = false) direction: Direction = Direction.DESC
    ): ResponseEntity<Page<GetDeviceCategoryResponse>> {
        val pageRequest = if (sort.isEmpty()) {
            PageRequest.of(pageNumber, pageSize)
        } else {
            PageRequest.of(pageNumber, pageSize, direction, *sort)
        }
        val page = deviceCategoryService.getPaginated(pageRequest)
        return ResponseEntity.ok(page.map { GetDeviceCategoryResponse(it) })
    }

    @RequiresPermission(API_DEVICES_CATEGORIES_GET)
    @GetMapping("/{id}")
    fun get(
        @PathVariable id: Long
    ): ResponseEntity<GetDeviceCategoryResponse> {
        val entity = deviceCategoryService.get(id)
            ?: throw DeviceCategoryNotFoundException(id)

        return ResponseEntity.ok(GetDeviceCategoryResponse(entity))
    }

    @RequiresPermission(API_LOCKERS_GET)
    @GetMapping("/{id}/devices")
    fun getDevices(
        @PathVariable id: Long
    ): ResponseEntity<Set<GetDeviceResponse>> {
        val entity = deviceCategoryService.get(id)
            ?: throw DeviceCategoryNotFoundException(id)

        val set = entity.devices.map { GetDeviceResponse(it) }.toSet()
        return ResponseEntity.ok(set)
    }

    @RequiresPermission(API_DEVICES_CATEGORIES_PATCH)
    @PatchMapping("/{id}")
    fun patch(
        @PathVariable id: Long,
        @RequestBody request: PatchDeviceCategoryRequest
    ): ResponseEntity<GetDeviceCategoryResponse> {
        val entity = deviceCategoryService.update(id, request)
        return ResponseEntity.ok(GetDeviceCategoryResponse(entity))
    }

    @RequiresPermission(API_DEVICES_CATEGORIES_DELETE)
    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: Long
    ): ResponseEntity<Void> {
        deviceCategoryService.delete(id)
        return ResponseEntity.noContent().build()
    }

}