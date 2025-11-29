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

    private val anonymous = setOf(
        API_AUTH__LOGIN,
        API_AUTH__REGISTER,

        API_RESOURCES_SUPPORT__CREATE
    )

    private val student = setOf(
        API_PROFILE__GET,
        API_PROFILE__PATCH,
        API_PROFILE__DELETE,

        API_AUTH__LOGOUT,

        API_RESOURCES_SUPPORT__CREATE
    )

    private val professor = setOf(
        API_PROFILE__GET,
        API_PROFILE__PATCH,
        API_PROFILE__DELETE,

        API_AUTH__LOGOUT,

        API_RESOURCES_SUPPORT__CREATE
    )

    private val administrator = Permission.entries.toSet()

    @Transactional
    override fun run(vararg args: String) {
        if (service.count() > 0)
            return

        service.create(CreateRoleRequest(name = "Anonymous", permissions = anonymous))
        service.create(CreateRoleRequest(name = "Student", permissions = student))
        service.create(CreateRoleRequest(name = "Professor", permissions = professor))
        service.create(CreateRoleRequest(name = "Administrator", permissions = administrator))
    }

}