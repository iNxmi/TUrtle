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
import kotlin.time.measureTime

@Service
class DailySchedule(
    private val configurationService: ConfigurationService,
    private val userService: UserService
) {

    private val logger = LoggerFactory.getLogger(DailySchedule::class.java)

    @Volatile
    private var ready = false

    @EventListener(ApplicationReadyEvent::class)
    fun onApplicationReady() {
        ready = true
    }

    @Scheduled(cron = "0 0 0 * * *")
    fun daily() {
        if (!ready) {
            logger.info("Skipping daily schedule due to not being ready.")
            return
        }

        logger.info("Running daily schedule...")
        val duration = run()
        logger.info("Finished daily schedule in $duration")
    }

    private fun run() = measureTime {
        deleteUnverifiedUsers()
    }

    private fun deleteUnverifiedUsers() {
        logger.info("Running deletion of unverified users...")

        val duration = configurationService.getTyped<Duration>(Key.USER_VERIFICATION_DURATION)
        val cutoffTime = Instant.now().minus(duration)

        val unverifiedUsers = userService.getByStatusEqualsAndCreatedAtBefore(Status.PENDING_VERIFICATION, cutoffTime)
        if (unverifiedUsers.isEmpty())
            return

        userService.deleteAll(unverifiedUsers)

        logger.info("Deleted ${unverifiedUsers.size} unverified users.")
    }

}