package de.csw.turtle.api.mapper

import de.csw.turtle.api.dto.create.CreateExceptionRequest
import de.csw.turtle.api.dto.get.GetExceptionResponse
import de.csw.turtle.api.dto.patch.PatchExceptionRequest
import de.csw.turtle.api.entity.ExceptionEntity
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
abstract class ExceptionMapper : CRUDMapper<
        ExceptionEntity,
        CreateExceptionRequest,
        GetExceptionResponse,
        PatchExceptionRequest
        > {

    override fun create(request: CreateExceptionRequest) = ExceptionEntity(
        endpoint = request.endpoint,
        exception = request.exception,
        message = request.message,
        stackTrace = request.stackTrace
    )

    override fun get(entity: ExceptionEntity) = GetExceptionResponse(
        id = entity.id,
        endpoint = entity.endpoint,
        exception = entity.exception,
        message = entity.message,
        stackTrace = entity.stackTrace,
        updatedAt = entity.updatedAt,
        createdAt = entity.createdAt
    )

    override fun patch(
        entity: ExceptionEntity,
        request: PatchExceptionRequest
    ): ExceptionEntity {
        request.endpoint?.let { entity.endpoint = it }
        request.exception?.let { entity.exception = it }
        request.message?.let { entity.message = it }
        request.stackTrace?.let { entity.stackTrace = it }
        return entity
    }

}