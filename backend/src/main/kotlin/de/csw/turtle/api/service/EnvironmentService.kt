package de.csw.turtle.api.service

import org.springframework.core.env.Environment
import org.springframework.stereotype.Service

@Service
class EnvironmentService(
    private val environment: Environment
) {

    fun isDev() = environment.activeProfiles.contains("dev")

}