package de.csw.turtle.api.mapper

import de.csw.turtle.api.dto.create.CRUDCreateRequest
import de.csw.turtle.api.dto.get.CRUDGetResponse
import de.csw.turtle.api.dto.patch.CRUDPatchRequest
import de.csw.turtle.api.entity.CRUDEntity

interface CRUDMapper<
        Entity : CRUDEntity,
        CreateRequest : CRUDCreateRequest,
        GetResponse : CRUDGetResponse,
        PatchRequest : CRUDPatchRequest
        > {

    fun create(request: CreateRequest): Entity

    fun get(entity: Entity): GetResponse

    fun patch(entity: Entity, request: PatchRequest): Entity

}