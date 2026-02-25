package de.csw.turtle.api.dto.get

import de.csw.turtle.api.entity.RoomBookingEntity
import java.time.Instant

data class GetRoomBookingResponse(
    override val id: Long?,

    val userId: Long?,
    val title: String?,
    val start: Instant?,
    val end: Instant?,
    val description: String?,
    val access: RoomBookingEntity.Access?,
    val whitelistedUserIds: Set<Long>?,
    val status: RoomBookingEntity.Status?,

    override val updatedAt: Instant?,
    override val createdAt: Instant?
) : GetResponse {

    constructor(entity: RoomBookingEntity) : this(
        id = entity.id,

        userId = entity.user.id,
        title = entity.title,
        start = entity.start,
        end = entity.end,
        description = entity.description,
        access = entity.access,
        whitelistedUserIds = entity.whitelistedUsers.map { it.id }.toSortedSet(),
        status = entity.status,

        updatedAt = entity.updatedAt,
        createdAt = entity.createdAt
    )

}