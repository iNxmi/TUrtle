package de.csw.turtle.api.controller.api

import de.csw.turtle.api.controller.DeleteController
import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.dto.get.GetExceptionResponse
import de.csw.turtle.api.entity.ExceptionEntity
import de.csw.turtle.api.mapper.ExceptionMapper
import de.csw.turtle.api.service.ExceptionService
import org.springframework.data.domain.Sort.Direction
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/exceptions")
class ExceptionController(
    override val service: ExceptionService,
    override val mapper: ExceptionMapper
) : GetController<ExceptionEntity, GetExceptionResponse>,
    DeleteController<ExceptionEntity> {

    @PreAuthorize("hasAuthority('MANAGE_EXCEPTIONS')")
    override fun get(id: Long) = super.get(id)

    @PreAuthorize("hasAuthority('MANAGE_EXCEPTIONS')")
    override fun getCollection(
        rsql: String?,

        pageNumber: Int?,
        pageSize: Int,

        sortProperty: String?,
        sortDirection: Direction
    ) = super.getCollection(rsql, pageNumber, pageSize, sortProperty, sortDirection)

    @PreAuthorize("hasAuthority('MANAGE_EXCEPTIONS')")
    override fun delete(id: Long) = super.delete(id)

}