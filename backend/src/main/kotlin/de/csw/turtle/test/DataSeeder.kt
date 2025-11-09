package de.csw.turtle.test

import de.csw.turtle.api.dto.request.CreateUserRequest
import de.csw.turtle.api.service.UserService
import io.github.serpro69.kfaker.Faker
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.Transactional
import kotlin.math.absoluteValue

@Configuration
class DataSeeder(
    private val userService: UserService
) {

    private val minimumUsers = 512
    private val faker = Faker()

    @Bean
    @Transactional
    fun seedUsers() = CommandLineRunner {
        val repository = userService.repository
        while (repository.count() < minimumUsers) {
            val firstName = faker.name.firstName()
            val lastName = faker.name.lastName()
            val email = faker.internet.email("$firstName $lastName")
            val username = ("$firstName.$lastName").lowercase()

            val createUserRequest = CreateUserRequest(
                username,
                firstName,
                lastName,
                email,
                faker.random.unique.nextInt().absoluteValue.toLong(),
                "password"
            )
            userService.create(createUserRequest)
        }
    }

}