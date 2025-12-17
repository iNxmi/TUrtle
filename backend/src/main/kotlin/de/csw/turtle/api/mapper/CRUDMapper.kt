package de.csw.turtle.api.mapper

import de.csw.turtle.api.entity.CRUDEntity

interface CRUDMapper<
        Entity : CRUDEntity,
        CreateRequest : de.csw.turtle.api.dto.create.CreateRequest,
        GetResponse : de.csw.turtle.api.dto.get.GetResponse,
        PatchRequest : de.csw.turtle.api.dto.patch.PatchRequest
        > {

    fun create(request: CreateRequest): Entity

    fun get(entity: Entity): GetResponse

    fun patch(entity: Entity, request: PatchRequest): Entity

}