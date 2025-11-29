package de.csw.turtle.api.service.locker

import de.csw.turtle.TUrtleProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LockerControlServiceConfig(
    private val properties: TUrtleProperties
) {

    @Bean
    fun lockerControlService(): LockerControlService {
        if (properties.debug)
            return DebugLockerControlService()

        return SSHLockerControlService(properties)
    }

}