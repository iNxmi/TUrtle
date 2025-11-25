package de.csw.turtle.test

import de.csw.turtle.api.dto.create.CreateSupportTicketRequest
import de.csw.turtle.api.entity.LockerEntity
import de.csw.turtle.api.entity.SupportTicketEntity
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.repository.LockerRepository
import de.csw.turtle.api.repository.SupportTicketRepository
import de.csw.turtle.api.repository.UserRepository
import de.csw.turtle.api.security.Role
import de.csw.turtle.api.service.PasswordEncoderService
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
    private val userRepository: UserRepository,
    private val lockerRepository: LockerRepository,
    private val passwordEncoderService: PasswordEncoderService,
    private val supportTicketRepository: SupportTicketRepository
) {

    private val minimumEntries = 128
    private val faker = Faker()

    @Bean
    @Transactional
    fun seedUsers() = CommandLineRunner {
        while (userRepository.count() < minimumEntries) {
            val firstName = faker.name.firstName()
            val lastName = faker.name.lastName()
            val email = faker.internet.email("$firstName $lastName")
            val username = ("$firstName.$lastName").lowercase()

            val entries = Role.entries
            val index = floor(Math.random() * (entries.size - 1)).toInt() + 1
            val role = entries[index]

            val user = UserEntity(
                userName = username,
                firstName = firstName,
                lastName = lastName,
                email = email,
                studentId = faker.random.unique.nextInt().absoluteValue.toLong(),
                passwordHash = passwordEncoderService.encode("password"),
                role = role
            )
            userRepository.save(user)
        }

        val admin = UserEntity(
            userName = "admin",
            firstName = "admin",
            lastName = "admin",
            email = "admin@csw.de",
            studentId = 42069,
            passwordHash = passwordEncoderService.encode("admin"),
            role = Role.ADMINISTRATOR
        )
        userRepository.save(admin)
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

    @Bean
    @Transactional
    fun seedLockers() = CommandLineRunner {
        val repository = lockerRepository

        val locker6 = LockerEntity(6, "Locker Nr. 6")
        repository.save(locker6)

        val locker7 = LockerEntity(7, "Locker Nr. 7")
        repository.save(locker7)
    }

}