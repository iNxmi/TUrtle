package de.csw.turtle.api.controller.api

import de.csw.turtle.api.Permission
import de.csw.turtle.api.controller.DeleteController
import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.dto.get.GetExceptionResponse
import de.csw.turtle.api.entity.ExceptionEntity
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.ForbiddenException
import de.csw.turtle.api.exception.UnauthorizedException
import de.csw.turtle.api.mapper.ExceptionMapper
import de.csw.turtle.api.service.ExceptionService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/exceptions")
class ExceptionController(
    private val exceptionService: ExceptionService,
    private val exceptionMapper: ExceptionMapper
) :
    GetController<ExceptionEntity, GetExceptionResponse>,
    DeleteController<ExceptionEntity> {

    override fun get(
        user: UserEntity?,
        id: Long
    ): ResponseEntity<GetExceptionResponse> {
        if (user == null)
            throw UnauthorizedException()

        if (!user.hasPermission(Permission.MANAGE_EXCEPTIONS))
            throw ForbiddenException()

        val entity = exceptionService.get(id)
        val dto = exceptionMapper.get(entity)
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
            throw UnauthorizedException()

        if (!user.hasPermission(Permission.MANAGE_EXCEPTIONS))
            throw ForbiddenException()

        val sort = sortProperty?.let {
            Sort.by(sortDirection, sortProperty)
        } ?: Sort.unsorted()

        if (pageNumber != null) {
            val pageable = PageRequest.of(pageNumber, pageSize, sort)
            val page = exceptionService.getPage(rsql = rsql, pageable = pageable)
            val dto = page.map { exceptionMapper.get(it) }
            return ResponseEntity.ok(dto)
        }

        val collection = exceptionService.getAll(rsql = rsql, sort = sort).toMutableSet()
        val dto = collection.map { exceptionMapper.get(it) }
        return ResponseEntity.ok(dto)
    }

    override fun delete(
        user: UserEntity?,
        id: Long
    ): ResponseEntity<Void> {
        if (user == null)
            throw UnauthorizedException()

        if (!user.hasPermission(Permission.MANAGE_EXCEPTIONS))
            throw ForbiddenException()
        
        exceptionService.delete(id)
        return ResponseEntity.noContent().build()
    }

}