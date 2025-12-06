package de.csw.turtle.api.config.defaults

import de.csw.turtle.api.Permission
import de.csw.turtle.api.Permission.*
import de.csw.turtle.api.dto.create.CreateRoleRequest
import de.csw.turtle.api.service.RoleService
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.Transactional

@Configuration
class DefaultRolesConfiguration(
    private val service: RoleService
) : CommandLineRunner {

    private val guest = setOf(
        BACKEND__API_AUTH__LOGIN,
        BACKEND__API_AUTH__REGISTER,

        BACKEND__API_SUPPORTTICKETS__CREATE,
        BACKEND__API_FAQ__GET
    )

    private val student = setOf(
        BACKEND__API_AUTH__LOGOUT,

        BACKEND__API_USER_PROFILE__GET,
        BACKEND__API_USER_PROFILE__PATCH,
        BACKEND__API_USER_PROFILE__DELETE,

        BACKEND__API_SUPPORTTICKETS__CREATE,
        BACKEND__API_FAQ__GET
    )

    private val professor = setOf(
        BACKEND__API_AUTH__LOGOUT,

        BACKEND__API_USER_PROFILE__GET,
        BACKEND__API_USER_PROFILE__PATCH,
        BACKEND__API_USER_PROFILE__DELETE,

        BACKEND__API_SUPPORTTICKETS__CREATE,
        BACKEND__API_FAQ__GET
    )

    private val administrator = Permission.entries.toSet()

    @Transactional
    override fun run(vararg args: String) {
        if (service.count() > 0)
            return

        service.create(CreateRoleRequest(name = "Guest", permissions = guest))
        service.create(CreateRoleRequest(name = "Student", permissions = student))
        service.create(CreateRoleRequest(name = "Professor", permissions = professor))
        service.create(CreateRoleRequest(name = "Administrator", permissions = administrator))
    }

}