package de.csw.turtle.api.dto.patch

import java.time.Instant

data class PatchRoomBookingRequest(
    val title: String? = null,
    val start: Instant? = null,
    val end: Instant? = null,
    val description: String? = null,
    val creator: Long? = null
) : CRUDPatchRequest