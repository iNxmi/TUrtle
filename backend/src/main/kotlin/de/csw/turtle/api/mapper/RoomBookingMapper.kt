package de.csw.turtle.api.mapper

import de.csw.turtle.api.dto.create.CreateRoomBookingRequest
import de.csw.turtle.api.dto.create.CreateUserRequest
import de.csw.turtle.api.dto.get.GetRoomBookingResponse
import de.csw.turtle.api.dto.patch.PatchRoomBookingRequest
import de.csw.turtle.api.entity.RoomBookingEntity
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.service.UserService
import org.mapstruct.Mapper
import org.springframework.beans.factory.annotation.Autowired

@Mapper(componentModel = "spring")
abstract class RoomBookingMapper : CRUDMapper<RoomBookingEntity, CreateRoomBookingRequest, GetRoomBookingResponse, PatchRoomBookingRequest>{

    @Autowired
    protected lateinit var userService: UserService
    @Autowired
    protected lateinit var userMapper: UserMapper

    override fun create(request: CreateRoomBookingRequest) = RoomBookingEntity(
        title = request.title,
        startTime = request.startTime,
        endTime = request.endTime,
        description = request.description,
        creator = userService.get(request.creator)
    )

    override fun get(entity: RoomBookingEntity) = GetRoomBookingResponse (
        id = entity.id,
        title = entity.title,
        startTime =  entity.startTime,
        endTime = entity.endTime,
        description = entity.description,
        creator = userMapper.get(entity.creator),
        whitelist = entity.whitelist?.map { user -> userMapper.get(user) }?.toSet(),
        createdAt = entity.createdAt
    )

    override fun patch(entity: RoomBookingEntity, request: PatchRoomBookingRequest) : RoomBookingEntity {
        request.title?.let { entity.title = it }
        request.startTime?.let { entity.startTime = it }
        request.endTime?.let { entity.endTime = it }
        request.description?.let {entity.description = it}
        request.creator?.let { entity.creator = userService.get(it) }
        request.whitelist?.let { entity.whitelist = it.map { user -> userService.get(user)}.toMutableSet() }

        return entity
    }

}