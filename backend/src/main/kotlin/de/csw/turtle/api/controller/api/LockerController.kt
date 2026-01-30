package de.csw.turtle.api.controller.api

import de.csw.turtle.api.Permission
import de.csw.turtle.api.controller.CreateController
import de.csw.turtle.api.controller.DeleteController
import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.controller.PatchController
import de.csw.turtle.api.dto.create.CreateLockerRequest
import de.csw.turtle.api.dto.get.GetLockerResponse
import de.csw.turtle.api.dto.patch.PatchLockerRequest
import de.csw.turtle.api.entity.LockerEntity
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.ForbiddenException
import de.csw.turtle.api.exception.UnauthorizedException
import de.csw.turtle.api.mapper.LockerMapper
import de.csw.turtle.api.service.locker.LockerService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/lockers")
class LockerController(
    private val lockerService: LockerService,
    private val lockerMapper: LockerMapper
) : CreateController<LockerEntity, CreateLockerRequest, GetLockerResponse>,
    GetController<LockerEntity, GetLockerResponse>,
    PatchController<LockerEntity, PatchLockerRequest, GetLockerResponse>,
    DeleteController<LockerEntity> {

    override fun create(
        user: UserEntity?,
        request: CreateLockerRequest
    ): ResponseEntity<GetLockerResponse> {
        if (user == null)
            throw UnauthorizedException()

        if (!user.hasPermission(Permission.MANAGE_LOCKERS))
            throw ForbiddenException()

        val entity = lockerService.create(request)
        val location = URI.create("/api/lockers/${entity.id}")
        val dto = lockerMapper.get(entity)
        return ResponseEntity.created(location).body(dto)
    }

    override fun get(
        user: UserEntity?,
        id: Long
    ): ResponseEntity<GetLockerResponse> {
        val entity = lockerService.get(id)
        val dto = lockerMapper.get(entity)
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
        val sort = sortProperty?.let {
            Sort.by(sortDirection, sortProperty)
        } ?: Sort.unsorted()

        if (pageNumber != null) {
            val pageable = PageRequest.of(pageNumber, pageSize, sort)
            val page = lockerService.getPage(rsql = rsql, pageable = pageable)
            val dto = page.map { lockerMapper.get(it) }
            return ResponseEntity.ok(dto)
        }

        val collection = lockerService.getAll(rsql = rsql, sort = sort).toMutableSet()
        val dto = collection.map { lockerMapper.get(it) }
        return ResponseEntity.ok(dto)
    }

    override fun patch(
        user: UserEntity?,
        id: Long,
        request: PatchLockerRequest
    ): ResponseEntity<GetLockerResponse> {
        if (user == null)
            throw UnauthorizedException()

        if (!user.hasPermission(Permission.MANAGE_LOCKERS))
            throw ForbiddenException()

        val entity = lockerService.patch(id, request)
        val dto = lockerMapper.get(entity)
        return ResponseEntity.ok(dto)
    }

    override fun delete(
        user: UserEntity?,
        id: Long
    ): ResponseEntity<Void> {
        if (user == null)
            throw UnauthorizedException()

        if (!user.hasPermission(Permission.MANAGE_LOCKERS))
            throw ForbiddenException()

        lockerService.delete(id)
        return ResponseEntity.noContent().build()
    }

}