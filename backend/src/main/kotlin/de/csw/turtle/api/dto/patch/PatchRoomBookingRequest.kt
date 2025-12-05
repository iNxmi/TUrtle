package de.csw.turtle.api.dto.patch

import de.csw.turtle.api.dto.get.GetUserResponse
import java.time.Instant

data class PatchRoomBookingRequest(
    val title: String? = null,
    val startTime: Instant? = null,
    val endTime: Instant? = null,
    val description: String? = null,
    val creator: Long? = null,
    val whitelist: Set<Long>? = null,
) : CRUDPatchRequest