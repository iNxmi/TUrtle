package de.csw.turtle.api.configuration.defaults

import de.csw.turtle.api.dto.create.CreateUserRequest
import de.csw.turtle.api.service.RoleService
import de.csw.turtle.api.service.UserService
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.transaction.annotation.Transactional

@Order(2)
@Configuration
class DefaultUsersConfiguration(
    private val userService: UserService,
    private val roleService: RoleService,
) : CommandLineRunner {

    //TODO implement randomized password and randomized emojis

    @Transactional
    override fun run(vararg args: String?) {
        if(userService.count() > 0)
            return

        val adminRole = roleService.getByName("Administrator")

        userService.create(CreateUserRequest(
            username="admin",
            firstName="Admin",
            lastName="Admin",
            email = "admin@csw.tu-darmstadt.de",
            emojis = "12345",
            password = "admin",
            roleIds = setOf(adminRole.id)
        ))
    }

}