package de.csw.turtle.api.service

import de.csw.turtle.api.dto.create.CreateExceptionRequest
import de.csw.turtle.api.dto.get.GetExceptionResponse
import de.csw.turtle.api.dto.patch.PatchExceptionRequest
import de.csw.turtle.api.entity.ExceptionEntity
import de.csw.turtle.api.mapper.ExceptionMapper
import de.csw.turtle.api.repository.ExceptionRepository
import org.springframework.stereotype.Service

@Service
class ExceptionService(
    override val repository: ExceptionRepository,
    override val mapper: ExceptionMapper
) : CRUDService<ExceptionEntity, CreateExceptionRequest, GetExceptionResponse, PatchExceptionRequest, ExceptionRepository, ExceptionMapper>()