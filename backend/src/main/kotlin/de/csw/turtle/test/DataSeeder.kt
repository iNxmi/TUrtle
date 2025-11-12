package de.csw.turtle.test

import de.csw.turtle.api.dto.request.CreateSupportTicketRequest
import de.csw.turtle.api.dto.request.CreateUserRequest
import de.csw.turtle.api.entity.SupportTicketEntity
import de.csw.turtle.api.repository.SupportTicketRepository
import de.csw.turtle.api.service.UserService
import io.github.serpro69.kfaker.Faker
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.Transactional
import kotlin.math.absoluteValue
import kotlin.math.floor

@Configuration
class DataSeeder(
    private val userService: UserService,
    private val supportTicketRepository: SupportTicketRepository
) {

    private val minimumEntries = 128
    private val faker = Faker()

    @Bean
    @Transactional
    fun seedUsers() = CommandLineRunner {
        val repository = userService.repository
        while (repository.count() < minimumEntries) {
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

    @Bean
    @Transactional
    fun seedSupportTickets() = CommandLineRunner {
        val repository = supportTicketRepository
        while (repository.count() < minimumEntries) {
            val entriesUrgency = SupportTicketEntity.Urgency.entries
            val indexUrgency = floor(Math.random() * entriesUrgency.size).toInt()
            val urgency = entriesUrgency[indexUrgency]

            val entriesCategory = SupportTicketEntity.Category.entries
            val indexCategory = floor(Math.random() * entriesCategory.size).toInt()
            val category = entriesCategory[indexCategory]

            val firstName = faker.name.firstName()
            val lastName = faker.name.lastName()
            val email = faker.internet.email("$firstName $lastName")

            val subject = "This is a support ticket about Something"
            val description = "I am facing an issue with Something. Please help!"

            val createSupportTicketRequest = CreateSupportTicketRequest(
                urgency,
                category,
                email,
                subject,
                description
            )

            val ticket = createSupportTicketRequest.create()
            repository.save(ticket)
        }
    }

}