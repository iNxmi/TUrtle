package de.csw.turtle.api.mapper

import de.csw.turtle.api.entity.CRUDEntity
import jakarta.transaction.Transactional

interface CRUDMapper<
        Entity : CRUDEntity,
        CreateRequest : de.csw.turtle.api.dto.create.CreateRequest,
        GetResponse : de.csw.turtle.api.dto.get.GetResponse,
        PatchRequest : de.csw.turtle.api.dto.patch.PatchRequest
        > {

    @Transactional
    fun create(request: CreateRequest): Entity

    @Transactional
    fun get(entity: Entity): GetResponse

    @Transactional
    fun patch(entity: Entity, request: PatchRequest): Entity

}