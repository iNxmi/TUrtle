package de.csw.turtle.api.controller.crud

import de.csw.turtle.api.Permission
import de.csw.turtle.api.dto.get.GetExceptionResponse
import de.csw.turtle.api.entity.ExceptionEntity
import de.csw.turtle.api.mapper.ExceptionMapper
import de.csw.turtle.api.service.ExceptionService
import de.csw.turtle.api.service.SecurityService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/exceptions")
class ExceptionController(
    override val getPermission: Permission = Permission.BACKEND__API_EXCEPTIONS__GET,
    override val deletePermission: Permission = Permission.BACKEND__API_EXCEPTIONS__DELETE,

    override val service: ExceptionService,
    override val mapper: ExceptionMapper,
    override val securityService: SecurityService
) : GetController<ExceptionEntity, GetExceptionResponse>,
    DeleteController<ExceptionEntity>