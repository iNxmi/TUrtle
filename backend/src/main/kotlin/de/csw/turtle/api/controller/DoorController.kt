package de.csw.turtle.api.controller

import de.csw.turtle.api.Permission
import de.csw.turtle.api.dto.hardware.OpenDoorEmojisRequest
import de.csw.turtle.api.entity.ConfigurationEntity.Key
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.entity.UserEntity.Status
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.service.*
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Duration
import java.time.LocalTime

private const val ENDPOINT = "/api/door"

@RestController
@RequestMapping(ENDPOINT)
class DoorController(
    private val userService: UserService,
    private val networkService: NetworkService,
    private val configurationService: ConfigurationService,
    private val roomBookingService: RoomBookingService,
    private val doorService: DoorService
) {

    @PostMapping("/emojis")
    fun door(
        @RequestBody request: OpenDoorEmojisRequest,
        httpRequest: HttpServletRequest
    ): ResponseEntity<Void> {
        val user = userService.getByEmojis(request.emojis)
            ?: userService.getByEmojisLegacyFix(request.emojis)

        if (user == null || user.status != Status.ACTIVE) {
            if (!networkService.isLocalNetwork(httpRequest))
                throw HttpException.Forbidden("External network.")

            throw HttpException.Unauthorized("Incorrect emojis.")
        }

        checkDoorPermissions(user, httpRequest)

        val duration = configurationService.getTyped<Duration>(Key.DOOR_OPEN_DURATION)
        doorService.unlock(duration)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/unlock")
    fun door(
        @AuthenticationPrincipal user: UserEntity?,
        request: HttpServletRequest
    ): ResponseEntity<Void> {
        if (user == null || user.status != Status.ACTIVE)
            throw HttpException.Unauthorized()

        checkDoorPermissions(user, request)

        val duration = configurationService.getTyped<Duration>(Key.DOOR_OPEN_DURATION)
        doorService.unlock(duration)
        return ResponseEntity.ok().build()
    }

    private fun checkDoorPermissions(user: UserEntity, request: HttpServletRequest) {
        if (user.hasPermission(Permission.MANAGE_DOOR))
            return

        if (!networkService.isLocalNetwork(request))
            throw HttpException.Forbidden("External network.")

        val start = configurationService.getTyped<LocalTime>(Key.DOOR_SCHEDULE_START)
        val end = configurationService.getTyped<LocalTime>(Key.DOOR_SCHEDULE_END)
        if (isNowBetween(start, end))
            throw HttpException.ServiceUnavailable("Outside of schedule. $start to $end.")

        val booking = roomBookingService.getCurrent() ?: return
        if (booking.whitelistedUsers.isNotEmpty() && !booking.whitelistedUsers.contains(user))
            throw HttpException.Unauthorized("User '${user.id}' not in whitelist for current Room Booking.")
    }

    private fun isNowBetween(start: LocalTime, end: LocalTime, now: LocalTime = LocalTime.now()) =
        now.isBefore(start) || now.isAfter(end)

}