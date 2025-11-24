package de.csw.turtle.api.service.door

import de.csw.turtle.TUrtleProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DoorControlServiceConfig(
    private val properties: TUrtleProperties
) {

    @Bean
    fun doorControlService(): DoorControlService {
        if (properties.debug)
            return DebugDoorControlService()

        return SSHDoorControlService(properties)
    }

}