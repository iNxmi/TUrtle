package de.csw.turtle.test

import de.csw.turtle.api.entity.*
import de.csw.turtle.api.repository.*
import de.csw.turtle.api.service.RoleService
import io.github.serpro69.kfaker.Faker
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.core.annotation.Order
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull
import kotlin.math.absoluteValue
import kotlin.math.floor

@Component
class DataSeeder(
    private val userRepository: UserRepository,
    private val lockerRepository: LockerRepository,
    private val deviceCategoryRepository: DeviceCategoryRepository,
    private val deviceRepository: DeviceRepository,
    private val passwordEncoder: PasswordEncoder,
    private val supportTicketRepository: SupportTicketRepository,
    private val roleService: RoleService
) {

    private val minimumEntries = 128
    private val faker = Faker()

    @EventListener(ApplicationReadyEvent::class)
    @Transactional
    fun seedUsers() {
        while (userRepository.count() < minimumEntries) {
            val firstName = faker.name.firstName()
            val lastName = faker.name.lastName()
            val email = faker.internet.email("$firstName $lastName")
            val username = ("$firstName.$lastName").lowercase()

            val roles = roleService.getAll()
            val index = floor(Math.random() * (roles.size - 1)).toInt() + 1
            val role = roles[index]

            val user = UserEntity(
                username = username,
                firstName = firstName,
                lastName = lastName,
                email = email,
                studentId = faker.random.unique.nextInt().absoluteValue.toLong(),
                password = passwordEncoder.encode("password"),
                roles = setOf(role).toMutableSet()
            )
            userRepository.save(user)
        }

        if (userRepository.findByUsername("admin") == null) {
            val admin = UserEntity(
                username = "admin",
                firstName = "admin",
                lastName = "admin",
                email = "admin@csw.de",
                studentId = 42069,
                password = passwordEncoder.encode("admin"),
                roles = mutableSetOf(roleService.getByName("Administrator")!!)
            )
            userRepository.save(admin)
        }
    }

    @EventListener(ApplicationReadyEvent::class)
    @Transactional
    fun seedSupportTickets() {
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
    @EventListener(ApplicationReadyEvent::class)
    @Transactional
    fun seedLockers() {
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
    @EventListener(ApplicationReadyEvent::class)
    @Transactional
    fun seedCategories() {
        val repository = deviceCategoryRepository

        if(repository.findByName("Laptops") == null) {
            val category = DeviceCategoryEntity("Laptops")
            repository.save(category)
        }

        if(repository.findByName("Consoles") == null) {
            val category = DeviceCategoryEntity("Consoles")
            repository.save(category)
        }

        if(repository.findByName("Projectors") == null) {
            val category = DeviceCategoryEntity("Projectors")
            repository.save(category)
        }
    }

    @Order(2)
    @EventListener(ApplicationReadyEvent::class)
    @Transactional
    fun seedDevices() {
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