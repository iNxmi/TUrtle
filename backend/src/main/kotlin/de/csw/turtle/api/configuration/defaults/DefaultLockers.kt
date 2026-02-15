package de.csw.turtle.api.configuration.defaults

import de.csw.turtle.api.service.locker.LockerService
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.Transactional

@Configuration
class DefaultLockers(
    private val service: LockerService
) : CommandLineRunner {

    @Transactional
    override fun run(vararg args: String) {
        if (service.count() > 0)
            return

        service.create(name = "Locker Nr. 6", index = 6, isSoftwareUnlockable = true, locked = false)
        service.create(name = "Locker Nr. 7", index = 7, isSoftwareUnlockable = true, locked = false)
    }

}