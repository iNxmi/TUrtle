package de.csw.turtle.api.configuration.defaults

import de.csw.turtle.api.entity.RoleEntity.Type
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.service.RoleService
import de.csw.turtle.api.service.UserService
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.transaction.annotation.Transactional

@Order(3)
@Configuration
class DefaultUsersConfiguration(
    private val userService: UserService,
    private val roleService: RoleService,
) : CommandLineRunner {

    //TODO implement randomized password and randomized emojis

    @Transactional
    override fun run(vararg args: String?) {
        if (userService.count() > 0)
            return

        val studentRole = roleService.getByType(Type.STUDENT) ?: throw IllegalStateException()
        userService.create(
            username = "student",
            firstName = "Student",
            lastName = "Student",
            email = "student@stud.tu-darmstadt.de",
            emojis = "🍉🍉🍉🍉🍉",
            password = "student",
            roleIds = setOf(studentRole.id),
            status = UserEntity.Status.ACTIVE
        )

        val professorRole = roleService.getByType(Type.PROFESSOR) ?: throw IllegalStateException()
        userService.create(
            username = "professor",
            firstName = "Professor",
            lastName = "Professor",
            email = "professor@tu-darmstadt.de",
            emojis = "🌍🌍🌍🌍🌍",
            password = "professor",
            roleIds = setOf(professorRole.id),
            status = UserEntity.Status.ACTIVE
        )

        val administratorRole = roleService.getByType(Type.ADMINISTRATOR) ?: throw IllegalStateException()
        userService.create(
            username = "admin",
            firstName = "Admin",
            lastName = "Admin",
            email = "admin@csw.tu-darmstadt.de",
            emojis = "💩💩💩💩💩",
            password = "admin",
            roleIds = setOf(administratorRole.id),
            status = UserEntity.Status.ACTIVE
        )
    }

}