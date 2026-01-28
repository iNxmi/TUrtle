package de.csw.turtle.api.controller.api

import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.controller.DeleteController
import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.dto.get.GetExceptionResponse
import de.csw.turtle.api.entity.ExceptionEntity
import org.springframework.data.domain.Sort.Direction
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/exceptions")
class ExceptionController :
    GetController<ExceptionEntity, GetExceptionResponse>,
    DeleteController<ExceptionEntity> {

    override fun get(
        user: UserEntity?,
        id: Long
    ): ResponseEntity<GetExceptionResponse> {
        TODO("Not yet implemented")
    }

    override fun getCollection(
        user: UserEntity?,
        rsql: String?,
        pageNumber: Int?,
        pageSize: Int,
        sortProperty: String?,
        sortDirection: Direction
    ): ResponseEntity<Any> {
        TODO("Not yet implemented")
    }

    override fun delete(
        user: UserEntity?,
        id: Long
    ): ResponseEntity<Void> {
        TODO("Not yet implemented")
    }

}