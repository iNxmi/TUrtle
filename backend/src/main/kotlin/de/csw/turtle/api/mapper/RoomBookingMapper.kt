package de.csw.turtle.api.mapper

import de.csw.turtle.api.dto.create.CreateRoomBookingRequest
import de.csw.turtle.api.dto.get.GetRoomBookingResponse
import de.csw.turtle.api.dto.patch.PatchRoomBookingRequest
import de.csw.turtle.api.entity.RoomBookingEntity
import de.csw.turtle.api.service.UserService
import org.mapstruct.Mapper
import org.springframework.beans.factory.annotation.Autowired

@Mapper(componentModel = "spring")
abstract class RoomBookingMapper :
    CRUDMapper<RoomBookingEntity, CreateRoomBookingRequest, GetRoomBookingResponse, PatchRoomBookingRequest> {

    @Autowired
    protected lateinit var userService: UserService

    override fun create(request: CreateRoomBookingRequest): RoomBookingEntity {
        val entity = RoomBookingEntity(
            title = request.title,
            start = request.start,
            end = request.end,
            description = request.description,
            creator = userService.get(request.creatorId)
        )

        request.accessibility?.let { entity.accessibility = it }
        request.whitelist?.let { whitelist ->
            entity.whitelist.clear()
            entity.whitelist.addAll(whitelist.map { userService.get(it) })
        }

        return entity
    }

    override fun get(entity: RoomBookingEntity) = GetRoomBookingResponse(
        id = entity.id,
        title = entity.title,
        start = entity.start,
        end = entity.end,
        description = entity.description,
        creatorId = entity.creator.id,
        accessibility = entity.accessibility,
        whitelist = entity.whitelist.map { it.id }.toSet(),
        createdAt = entity.createdAt
    )

    override fun patch(entity: RoomBookingEntity, request: PatchRoomBookingRequest): RoomBookingEntity {

        request.title?.let { entity.title = it }
        request.start?.let { entity.start = it }
        request.end?.let { entity.end = it }
        request.description?.let { entity.description = it }
        request.creatorId?.let { entity.creator = userService.get(it) }
        request.accessibility?.let { entity.accessibility = it }

        request.whitelist?.let { whitelist ->
            entity.whitelist.clear()
            entity.whitelist.addAll(whitelist.map { userService.get(it) })
        }

        return entity
    }

}