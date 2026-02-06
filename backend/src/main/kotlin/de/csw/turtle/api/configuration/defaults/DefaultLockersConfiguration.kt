package de.csw.turtle.api.configuration.defaults

import de.csw.turtle.api.service.locker.LockerService
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.Transactional

@Configuration
class DefaultLockersConfiguration(
    private val service: LockerService
) : CommandLineRunner {

    @Transactional
    override fun run(vararg args: String) {
        if (service.count() > 0)
            return

        service.create(name = "3rd Locker", index = 3, isSoftwareUnlockable = false, false)
        service.create(name = "4th Locker", index = 4, isSoftwareUnlockable = false, false)
        service.create(name = "6th Locker", index = 6, isSoftwareUnlockable = true, false)
        service.create(name = "7th Locker", index = 7, isSoftwareUnlockable = true, false)
    }

}