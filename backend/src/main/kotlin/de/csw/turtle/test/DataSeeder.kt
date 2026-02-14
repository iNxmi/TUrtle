package de.csw.turtle.test

import de.csw.turtle.api.service.*
import de.csw.turtle.api.service.locker.LockerService
import io.github.serpro69.kfaker.Faker
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.core.annotation.Order
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit
import kotlin.math.floor
import kotlin.random.Random

@Component
class DataSeeder(
    private val lockerService: LockerService,
    private val itemCategoryService: ItemCategoryService,
    private val itemService: ItemService,
    private val supportTicketService: SupportTicketService,
    private val roleService: RoleService,
    private val roomBookingService: RoomBookingService,
    private val userService: UserService
) {

    private val faker = Faker()

    @EventListener(ApplicationReadyEvent::class)
    @Order(1)
    fun seedUsers() {
        while (userService.count() < 512) {
            val firstName = faker.name.firstName()
            val lastName = faker.name.lastName()
            val email = faker.internet.email("$firstName $lastName")
            val username = ("$firstName.$lastName").lowercase()

            val roles = roleService.getAll().toList()
            val index = floor(Math.random() * (roles.size - 1)).toInt() + 1
            val role = roles[index]

            val bCrypt = BCryptPasswordEncoder()

            val legacy = Math.random() in 0.0f..0.1f
            val emojis = userService.generateEmojis()

            try {
                userService.create(
                    username = username,
                    firstName = if (legacy) emojis else firstName,
                    lastName = lastName,
                    email = email,
                    emojis = if (legacy) bCrypt.encode(emojis) else emojis,
                    password = username,
                    roleIds = setOf(role.id),
                    status = randomEnum()
                )
            } catch (_: Exception) {
            }
        }
    }

    @Order(2)
    @EventListener(ApplicationReadyEvent::class)
    fun seedRoomBookings() {
        val service = roomBookingService
        while (service.count() < 24) {
            // minutes * hours * days * weeks * months
            val timeSpan = Duration.ofDays(45)
            val random = Random.nextFloat() * 2.0F - 1.0F
            val offsetMinutes = (timeSpan.toMinutes() * random).toLong()
            val start = Instant.now().plus(offsetMinutes, ChronoUnit.MINUTES)

            val duration = Duration.ofMinutes((Math.random() * 150 + 30).toLong())
            val end = start.plus(duration.toMinutes(), ChronoUnit.MINUTES)

            try {
                roomBookingService.create(
                    userId = userService.getByUsername("admin").id,
                    title = "${service.count()}: this is an event",
                    description = "this is the very long description",
                    start = start,
                    end = end,
                    accessibility = randomEnum(),
                    whitelistedUserIds = setOf(),
                    status = randomEnum()
                )
            } catch (_: Exception) {
            }
        }
    }

    @EventListener(ApplicationReadyEvent::class)
    fun seedSupportTickets() {
        while (supportTicketService.count() < 32) {
            val firstName = faker.name.firstName()
            val lastName = faker.name.lastName()
            val email = faker.internet.email("$firstName $lastName")

            val subject = "This is a support ticket about Something"
            val description = "I am facing an issue with Something. Please help!"

            try {
                supportTicketService.create(
                    urgency = randomEnum(),
                    category = randomEnum(),
                    email = email,
                    subject = subject,
                    description = description,
                    status = randomEnum()
                )
            } catch (_: Exception) {

            }

        }
    }

    @Order(2)
    @EventListener(ApplicationReadyEvent::class)
    fun seedDevices() {
        if (itemService.count() > 0)
            return

        itemService.create(
            name = "Xbox 360",
            description = "The Xbox 360 (we love it)",
            categoryId = itemCategoryService.getByName("Gaming").id,
            lockerId = lockerService.getByIndex(6)!!.id,
            needsConfirmation = true,
            acquiredAt = Instant.now()
        )

        itemService.create(
            name = "PlayStation 4",
            description = "The PS4 (we love it not as much as the 360)",
            categoryId = itemCategoryService.getByName("Gaming").id,
            lockerId = lockerService.getByIndex(6)!!.id,
            needsConfirmation = true,
            acquiredAt = Instant.now()
        )

        itemService.create(
            name = "Dell Laptop",
            description = "laptop from dell",
            categoryId = itemCategoryService.getByName("Laptop").id,
            lockerId = lockerService.getByIndex(7)!!.id,
            needsConfirmation = false,
            acquiredAt = Instant.now()
        )
    }

    private inline fun <reified T : Enum<T>> randomEnum(): T = enumValues<T>().random()


}