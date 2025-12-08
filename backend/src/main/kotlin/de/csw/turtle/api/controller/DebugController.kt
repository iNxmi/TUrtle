package de.csw.turtle.api.controller

import de.csw.turtle.api.Permission
import de.csw.turtle.api.dto.get.GetRoomBookingResponse
import de.csw.turtle.api.exception.exceptions.debug.DebugException
import de.csw.turtle.api.exception.exceptions.locker.LockerNotFoundException
import de.csw.turtle.api.mapper.RoomBookingMapper
import de.csw.turtle.api.service.EmailService
import de.csw.turtle.api.service.PermissionService
import de.csw.turtle.api.service.RoomBookingService
import de.csw.turtle.api.service.door.DoorControlService
import de.csw.turtle.api.service.locker.LockerControlService
import de.csw.turtle.api.service.locker.LockerService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.time.Duration
import java.time.Instant

@RestController
@RequestMapping("/debug")
class DebugController(
    private val doorControlService: DoorControlService,
    private val lockerControlService: LockerControlService,
    private val emailService: EmailService,
    private val lockerService: LockerService,
    private val permissionService: PermissionService,
    private val roomBookingService: RoomBookingService,
    private val roomBookingMapper: RoomBookingMapper

) {

    @GetMapping("/info")
    fun debug(
        httpRequest: HttpServletRequest
    ): ResponseEntity<Map<String, String>> {
        permissionService.check(Permission.BACKEND__DEBUG__INFO)

        val origin = URI.create(httpRequest.requestURL.toString()).host

        val map = mapOf("origin" to origin)
        return ResponseEntity.ok(map)
    }

    @GetMapping("/door")
    fun door(@RequestParam duration: Duration): ResponseEntity<String> {
        permissionService.check(Permission.BACKEND__DEBUG__DOOR)

        val response = doorControlService.trigger(duration)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/locker")
    fun door(@RequestParam id: Long): ResponseEntity<String> {
        permissionService.check(Permission.BACKEND__DEBUG__LOCKER)

        val locker = lockerService.getOrNull(id)
            ?: throw LockerNotFoundException(id)

        val response = lockerControlService.trigger(locker)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/exception")
    fun exception(@RequestParam message: String?): Nothing {
        permissionService.check(Permission.BACKEND__DEBUG__EXCEPTION)

        throw DebugException(message)
    }

    @GetMapping("/email")
    fun exception(@RequestParam to: String, @RequestParam subject: String, @RequestParam text: String) {
        permissionService.check(Permission.BACKEND__DEBUG__EMAIL)

        emailService.sendSimpleEmail(to, subject, text)
    }

}