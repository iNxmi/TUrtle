package de.csw.turtle.test

import de.csw.turtle.api.entity.*
import de.csw.turtle.api.repository.*
import de.csw.turtle.api.service.PasswordEncoderService
import de.csw.turtle.api.service.RoleService
import io.github.serpro69.kfaker.Faker
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull
import kotlin.math.absoluteValue
import kotlin.math.floor

@Configuration
class DataSeeder(
    private val userRepository: UserRepository,
    private val lockerRepository: LockerRepository,
    private val deviceCategoryRepository: DeviceCategoryRepository,
    private val deviceRepository: DeviceRepository,
    private val passwordEncoderService: PasswordEncoderService,
    private val supportTicketRepository: SupportTicketRepository,
    private val roleService: RoleService
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

            val roles = roleService.getAll()
            val index = floor(Math.random() * (roles.size - 1)).toInt() + 1
            val role = roles[index]

            val user = UserEntity(
                userName = username,
                firstName = firstName,
                lastName = lastName,
                email = email,
                studentId = faker.random.unique.nextInt().absoluteValue.toLong(),
                passwordHash = passwordEncoderService.encode("password"),
                roles = setOf(role).toMutableSet()
            )
            userRepository.save(user)
        }

        if (userRepository.findByUserName("admin") == null) {
            val admin = UserEntity(
                userName = "admin",
                firstName = "admin",
                lastName = "admin",
                email = "admin@csw.de",
                studentId = 42069,
                passwordHash = passwordEncoderService.encode("admin"),
                roles = mutableSetOf(roleService.getByName("Administrator")!!)
            )
            userRepository.save(admin)
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

            val ticket = SupportTicketEntity(
                urgency = urgency,
                category = category,
                email = email,
                subject = subject,
                description = description
            )
            repository.save(ticket)
        }
    }

    @Order(1)
    @Bean
    @Transactional
    fun seedLockers() = CommandLineRunner {
        val repository = lockerRepository

        if (lockerRepository.findByIndex(6) == null) {
            val locker6 = LockerEntity(6, "Locker Nr. 6")
            repository.save(locker6)
        }

        if (lockerRepository.findByIndex(7) == null) {
            val locker7 = LockerEntity(7, "Locker Nr. 7")
            repository.save(locker7)
        }
    }

    @Order(1)
    @Bean
    @Transactional
    fun seedCategories() = CommandLineRunner {
        val repository = deviceCategoryRepository

        val categoryLaptops = DeviceCategoryEntity("Laptops")
        repository.save(categoryLaptops)

        val categoryConsoles = DeviceCategoryEntity("Consoles")
        repository.save(categoryConsoles)

        val categoryProjectors = DeviceCategoryEntity("Projectors")
        repository.save(categoryProjectors)
    }

    @Order(2)
    @Bean
    @Transactional
    fun seedDevices() = CommandLineRunner {
        val repository = deviceRepository

        val xbox360 = DeviceEntity(
            name = "Xbox 360",
            description = "The Xbox 360 (we love it)",
            inventoryId = "x360_1",
            category = deviceCategoryRepository.findById(2).getOrNull()!!,
            locker = lockerRepository.findByIndex(6)!!
        )
        repository.save(xbox360)

        val ps4 = DeviceEntity(
            name = "PlayStation 4",
            description = "The PS4 (we love it not as much as the 360)",
            inventoryId = "ps4_1",
            category = deviceCategoryRepository.findById(2).getOrNull()!!,
            locker = lockerRepository.findByIndex(6)!!
        )
        repository.save(ps4)

        val laptop = DeviceEntity(
            name = "Dell Laptop",
            description = "laptop from dell",
            inventoryId = "dell_laptop_00_1",
            category = deviceCategoryRepository.findById(1).getOrNull()!!,
            locker = lockerRepository.findByIndex(7)!!
        )
        repository.save(laptop)
    }

}