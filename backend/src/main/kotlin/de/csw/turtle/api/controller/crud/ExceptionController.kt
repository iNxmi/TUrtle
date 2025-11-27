package de.csw.turtle.api.controller.crud

import de.csw.turtle.api.dto.create.CreateExceptionRequest
import de.csw.turtle.api.dto.get.GetExceptionResponse
import de.csw.turtle.api.dto.patch.PatchExceptionRequest
import de.csw.turtle.api.entity.ExceptionEntity
import de.csw.turtle.api.mapper.ExceptionMapper
import de.csw.turtle.api.repository.ExceptionRepository
import de.csw.turtle.api.service.ExceptionService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/exceptions")
class ExceptionController(
    override val service: ExceptionService,
    override val mapper: ExceptionMapper,
) : CRUDController<ExceptionEntity, CreateExceptionRequest, GetExceptionResponse, PatchExceptionRequest, ExceptionRepository, ExceptionMapper, ExceptionService>(
    "/api/exceptions",
    "api.exceptions"
)