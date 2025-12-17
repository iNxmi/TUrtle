package de.csw.turtle.api.controller.api

import de.csw.turtle.api.Permission
import de.csw.turtle.api.Permission.*
import de.csw.turtle.api.controller.DeleteController
import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.dto.get.GetExceptionResponse
import de.csw.turtle.api.entity.ExceptionEntity
import de.csw.turtle.api.mapper.ExceptionMapper
import de.csw.turtle.api.service.ExceptionService
import de.csw.turtle.api.service.PermissionService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/exceptions")
class ExceptionController(
    override val permissionGet: Permission = BACKEND__API_EXCEPTIONS__GET,
    override val permissionDelete: Permission = BACKEND__API_EXCEPTIONS__DELETE,

    override val service: ExceptionService,
    override val mapper: ExceptionMapper,
    override val permissionService: PermissionService
) : GetController<ExceptionEntity, GetExceptionResponse>,
    DeleteController<ExceptionEntity>