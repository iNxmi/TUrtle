package de.csw.turtle.api.config.defaults

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
        "api.auth:login",
        "api.auth:register",

        "api.support:create"
    )

    private val student = setOf(
        "api.profile:get",
        "api.profile:patch",
        "api.profile:delete",

        "api.auth:logout",

        "api.support:create"
    )

    private val professor = setOf(
        "api.profile:get",
        "api.profile:patch",
        "api.profile:delete",

        "api.auth:logout",

        "api.support:create"
    )

    private val administrator = setOf(
        "debug:info",
        "debug:door",
        "debug:locker",
        "debug:exception",
        "debug:email",

        "api.auth:login",
        "api.auth:register",
        "api.auth:logout",

        "api.profile:get",
        "api.profile:patch",
        "api.profile:delete",

        "api.auditlogs:create",
        "api.auditlogs:get",
        "api.auditlogs:patch",
        "api.auditlogs:delete",

        "api.devicecategories:create",
        "api.devicecategories:get",
        "api.devicecategories:patch",
        "api.devicecategories:delete",

        "api.devices:create",
        "api.devices:get",
        "api.devices:patch",
        "api.devices:delete",

        "api.exceptions:create",
        "api.exceptions:get",
        "api.exceptions:patch",
        "api.exceptions:delete",

        "api.lockers:create",
        "api.lockers:get",
        "api.lockers:patch",
        "api.lockers:delete",

        "api.support:create",
        "api.support:get",
        "api.support:patch",
        "api.support:delete",

        "api.users:create",
        "api.users:get",
        "api.users:patch",
        "api.users:delete",

        "api.roles:create",
        "api.roles:get",
        "api.roles:patch",
        "api.roles:delete"
    )

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