package de.csw.turtle.api.controller

import de.csw.turtle.api.dto.create.CreateLockerRequest
import de.csw.turtle.api.dto.get.GetDeviceResponse
import de.csw.turtle.api.dto.get.GetLockerResponse
import de.csw.turtle.api.dto.patch.PatchLockerRequest
import de.csw.turtle.api.exception.exceptions.locker.LockerNotFoundException
import de.csw.turtle.api.service.LockerService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort.Direction
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api/lockers")
class LockerController(
    private val lockerService: LockerService
) {

    @PostMapping
    fun create(
        @RequestBody createLockerRequest: CreateLockerRequest
    ): ResponseEntity<GetLockerResponse> {
        val entity = lockerService.create(createLockerRequest)
        return ResponseEntity
            .created(URI.create("/api/lockers/${entity.id}"))
            .body(GetLockerResponse(entity))
    }

    @GetMapping
    fun getPaginated(
        @RequestParam(name = "page", required = false) pageNumber: Int = 0,
        @RequestParam(name = "size", required = false) pageSize: Int = 20,
        @RequestParam(name = "sort", required = false) sort: Array<String> = emptyArray(),
        @RequestParam(name = "direction", required = false) direction: Direction = Direction.DESC
    ): ResponseEntity<Page<GetLockerResponse>> {
        val pageRequest = if (sort.isEmpty()) {
            PageRequest.of(pageNumber, pageSize)
        } else {
            PageRequest.of(pageNumber, pageSize, direction, *sort)
        }
        val page = lockerService.getPaginated(pageRequest)
        return ResponseEntity.ok(page.map { GetLockerResponse(it) })
    }

    @GetMapping("/{id}")
    fun get(
        @PathVariable id: Long
    ): ResponseEntity<GetLockerResponse> {
        val entity = lockerService.get(id)
            ?: throw LockerNotFoundException(id)

        return ResponseEntity.ok(GetLockerResponse(entity))
    }

    @GetMapping("/{id}/devices")
    fun getDevices(
        @PathVariable id: Long
    ): ResponseEntity<Set<GetDeviceResponse>> {
        val entity = lockerService.get(id)
            ?: throw LockerNotFoundException(id)

        val set = entity.devices.map { GetDeviceResponse(it) }.toSet()
        return ResponseEntity.ok(set)
    }

    @PatchMapping("/{id}")
    fun patch(
        @PathVariable id: Long,
        @RequestBody patchLockerRequest: PatchLockerRequest
    ): ResponseEntity<GetLockerResponse> {
        val entity = lockerService.update(id, patchLockerRequest)
        return ResponseEntity.ok(GetLockerResponse(entity))
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: Long
    ): ResponseEntity<Void> {
        lockerService.delete(id)
        return ResponseEntity.noContent().build()
    }


}