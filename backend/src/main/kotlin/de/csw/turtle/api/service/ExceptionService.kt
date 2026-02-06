package de.csw.turtle.api.service

import de.csw.turtle.api.entity.ExceptionEntity
import de.csw.turtle.api.repository.ExceptionRepository
import org.springframework.stereotype.Service

@Service
class ExceptionService(
    override val repository: ExceptionRepository
) : CRUDService<ExceptionEntity>() {

    fun create(
        endpoint: String,
        exception: String?,
        message: String?,
        stackTrace: String
    ): ExceptionEntity {
        val entity = ExceptionEntity(
            endpoint = endpoint,
            exception = exception,
            message = message,
            stackTrace = stackTrace
        )
        return repository.save(entity)
    }

}