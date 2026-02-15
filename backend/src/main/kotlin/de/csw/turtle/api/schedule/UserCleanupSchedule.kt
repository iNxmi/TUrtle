package de.csw.turtle.api.schedule

import de.csw.turtle.api.entity.ConfigurationEntity.Key
import de.csw.turtle.api.entity.UserEntity.Status
import de.csw.turtle.api.service.ConfigurationService
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
    private val configurationService: ConfigurationService,
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

        val duration = configurationService.getTyped<Duration>(Key.USER_VERIFICATION_DURATION)
        val cutoffTime = Instant.now().minus(duration)

        val unverifiedUsers = userService.getByStatusEqualsAndCreatedAtBefore(Status.PENDING_VERIFICATION, cutoffTime)
        if (unverifiedUsers.isEmpty())
            return

        userService.deleteAll(unverifiedUsers)
        logger.info("Users deleted: $unverifiedUsers")
    }

}