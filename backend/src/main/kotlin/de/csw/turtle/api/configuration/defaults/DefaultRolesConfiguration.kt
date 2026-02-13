package de.csw.turtle.api.configuration.defaults

import de.csw.turtle.api.Permission
import de.csw.turtle.api.entity.RoleEntity
import de.csw.turtle.api.entity.RoleEntity.Type
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

    private val professor = setOf(
        Permission.REQUEST_ROOM_BOOKINGS
    )

    private val administrator = Permission.entries.toSet()

    private fun create(name:String, permissions: Set<Permission>, type: Type) {
        if(service.existsByType(type))
            return

        service.create(name, permissions, type)
    }

    @Transactional
    override fun run(vararg args: String) {
        create(name = "Student", permissions = student, type= Type.STUDENT)
        create(name = "Professor", permissions = professor, type = Type.PROFESSOR)
        create(name = "Administrator", permissions = administrator, type= Type.ADMINISTRATOR)
    }

}