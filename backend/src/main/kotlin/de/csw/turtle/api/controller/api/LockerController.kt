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
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.service.locker.LockerService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

private const val ENDPOINT = "/api/lockers"

@RestController
@RequestMapping(ENDPOINT)
class LockerController(
    private val lockerService: LockerService
) : CreateController<LockerEntity, CreateLockerRequest, GetLockerResponse>,
    GetController<LockerEntity, Long, GetLockerResponse>,
    PatchController<LockerEntity, PatchLockerRequest, GetLockerResponse>,
    DeleteController<LockerEntity> {

    override fun create(
        user: UserEntity?,

        request: CreateLockerRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetLockerResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_LOCKERS))
            throw HttpException.Forbidden()

        val entity = lockerService.create(
            name = request.name,
            index = request.index,
            isSoftwareUnlockable = request.isSoftwareUnlockable,
            locked = request.locked
        )

        val location = URI.create("$ENDPOINT/${entity.id}")
        val dto = GetLockerResponse(entity)
        return ResponseEntity.created(location).body(dto)
    }

    override fun get(
        user: UserEntity?,

        variable: Long,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetLockerResponse> {
        val entity = lockerService.getById(variable)
            ?: throw HttpException.NotFound()

        val dto = GetLockerResponse(entity)
        return ResponseEntity.ok(dto)
    }

    override fun getCollection(
        user: UserEntity?,

        rsql: String?,
        pageNumber: Int?,
        pageSize: Int,
        sortProperty: String?,
        sortDirection: Sort.Direction,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<Any> {
        val sort = sortProperty?.let {
            Sort.by(sortDirection, sortProperty)
        } ?: Sort.unsorted()

        if (pageNumber != null) {
            val pageable = PageRequest.of(pageNumber, pageSize, sort)
            val page = lockerService.getPage(rsql = rsql, pageable = pageable)
            val dto = page.map { GetLockerResponse(it) }
            return ResponseEntity.ok(dto)
        }

        val collection = lockerService.getAll(rsql = rsql, sort = sort).toMutableSet()
        val dto = collection.map { GetLockerResponse(it) }
        return ResponseEntity.ok(dto)
    }

    override fun patch(
        user: UserEntity?,

        id: Long,
        request: PatchLockerRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetLockerResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_LOCKERS))
            throw HttpException.Forbidden()

        val entity = lockerService.patch(
            id = id,
            name = request.name,
            index = request.index,
            isSoftwareUnlockable = request.isSoftwareUnlockable,
            locked = request.locked
        )

        val dto = GetLockerResponse(entity)
        return ResponseEntity.ok(dto)
    }

    override fun delete(
        user: UserEntity?,

        id: Long,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<Void> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_LOCKERS))
            throw HttpException.Forbidden()

        lockerService.delete(id)
        return ResponseEntity.noContent().build()
    }

}