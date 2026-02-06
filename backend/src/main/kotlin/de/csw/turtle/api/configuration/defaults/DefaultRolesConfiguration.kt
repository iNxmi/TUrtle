package de.csw.turtle.api.configuration.defaults

import de.csw.turtle.api.Permission
import de.csw.turtle.api.Permission.*
import de.csw.turtle.api.dto.create.CreateRoleRequest
import de.csw.turtle.api.service.RoleService
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.transaction.annotation.Transactional

@Order(1)
@Configuration
class DefaultRolesConfiguration(
    private val service: RoleService
) : CommandLineRunner {

    private val student = setOf<Permission>()

    private val professor = setOf<Permission>()

    private val administrator = Permission.entries.toSet()

    @Transactional
    override fun run(vararg args: String) {
        if (service.count() > 0)
            return

        service.create(name = "Student", permissions = student)
        service.create(name = "Professor", permissions = professor)
        service.create(name = "Administrator", permissions = administrator)
    }

}