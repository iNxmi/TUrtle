package de.csw.turtle.schedule

import de.csw.turtle.api.service.SystemSettingService
import de.csw.turtle.api.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant

@Service
class UserCleanupSchedule(
    private val systemSettingService: SystemSettingService,
    private val userService: UserService
) {

    private val logger = LoggerFactory.getLogger(UserCleanupSchedule::class.java)

    @Volatile
    private var ready = false

    @EventListener(ApplicationReadyEvent::class)
    fun onApplicationReady() {
        ready = true
    }

    @Scheduled(cron = "0 0 * * * *")
    fun deleteUnverifiedUsers() {
        if (!ready)
            return

        val duration = systemSettingService.getTyped<Duration>("user.verification.duration")
        val cutoffTime = Instant.now().minus(duration)

        val unverifiedUsers = userService.getUnverifiedUsers(cutoffTime)
        if (unverifiedUsers.isEmpty())
            return

        userService.deleteAll(unverifiedUsers)
        logger.info("Users deleted: $unverifiedUsers")
    }

}