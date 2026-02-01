package de.csw.turtle.api.service

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

//60M * 60S * 1000MS
private const val PERIOD = 60L * 60L * 1000L

@Service
class UserCleanupService(
    private val systemSettingService: SystemSettingService,
    private val userService: UserService
) {

    @Scheduled(fixedRate = PERIOD)
    fun deleteUnverifiedUsers() {

    }

}