package de.csw.turtle.api.mapper

import de.csw.turtle.api.dto.create.CreateRoomBookingRequest
import de.csw.turtle.api.dto.get.GetRoomBookingResponse
import de.csw.turtle.api.dto.patch.PatchRoomBookingRequest
import de.csw.turtle.api.entity.RoomBookingEntity
import de.csw.turtle.api.entity.UserEntity
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
            creator = userService.get(request.creator),
            enableWhitelist = request.enableWhitelist
        )

        if (request.whitelist != null) {
            val users = request.whitelist.map { userService.get(it) }
            entity.whitelistedUsers.clear()
            entity.whitelistedUsers.addAll(users)
        }

        return entity
    }

    override fun get(entity: RoomBookingEntity) = GetRoomBookingResponse(
        id = entity.id,
        title = entity.title,
        start = entity.start,
        end = entity.end,
        description = entity.description,
        creator = entity.creator.id,
        createdAt = entity.createdAt,
        enableWhitelist = entity.enableWhitelist,
        whitelist = entity.whitelistedUsers.map { user -> user.id }.toSet()
    )

    override fun patch(entity: RoomBookingEntity, request: PatchRoomBookingRequest): RoomBookingEntity {

        request.title?.let { entity.title = it }
        request.start?.let { entity.start = it }
        request.end?.let { entity.end = it }
        request.description?.let { entity.description = it }
        request.creator?.let { entity.creator = userService.get(it) }
        request.enableWhitelist?.let { entity.enableWhitelist = it }

        if (request.whitelist != null) {
            entity.whitelistedUsers.clear()

            for (id in request.whitelist)
                entity.whitelistedUsers.add(userService.get(id))
        }

        return entity
    }

}