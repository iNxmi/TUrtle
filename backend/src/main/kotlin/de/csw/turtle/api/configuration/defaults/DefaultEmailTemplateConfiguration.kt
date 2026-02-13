package de.csw.turtle.api.configuration.defaults

import de.csw.turtle.api.entity.EmailTemplateEntity
import de.csw.turtle.api.entity.EmailTemplateEntity.Type
import de.csw.turtle.api.service.EmailTemplateService
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.transaction.annotation.Transactional

@Order(1)
@Configuration
class DefaultEmailTemplateConfiguration(
    private val service: EmailTemplateService
) : CommandLineRunner {

    val usersCreated = """
        Welcome <span th:text="${'$'}{user.firstName}">user.firstName</span>,
        <br>
        You have successfully created your CSW account.
    """.trimIndent()

    val usersVerify = """
        To verify your account please click <a th:href="${'$'}{url}">here</a>.
        <br>
        If you don't verify in <span th:text="${'$'}{duration.toDays()}">duration.toDays()</span> Days (<span th:text="${'$'}{user.createdAt.plusMillis(duration.toMillis())}">user.createdAt.plusMillis(duration.toMillis())</span>), your account will be deleted.
    """.trimIndent()

    val roomBookingsCreated = """
        Your room booking has been created. 
        <br>
        ID: <span th:text="${'$'}{booking.id}">booking.id</span>
        <br>
        Status: <span th:text="${'$'}{booking.status}">booking.status</span>
    """.trimIndent()

    val roomBookingsUpdated = """
        Your room booking has been updated. 
        <br>
        ID: <span th:text="${'$'}{post.id}">post.id</span>
        <br>
        Status: <span th:text="${'$'}{pre.status}">pre.status</span> -> <span th:text="${'$'}{post.status}">post.status</span>
    """.trimIndent()

    private fun create(name: String, title: String, content: String, type: Type)  {
        if(service.existsByType(type))
            return

        service.create(name, "Default value by TUrtle dev team.", title, content, type)
    }

    @Transactional
    override fun run(vararg args: String) {
        create("User Created", "CSW - Welcome to TUrtle", usersCreated, Type.USER_CREATED)
        create("User Verification", "CSW - Please verify your account", usersVerify, Type.USER_VERIFICATION)

        create("Room Booking Created", "CSW - Room Booking Created", roomBookingsCreated, Type.ROOM_BOOKING_CREATED)
        create("Room Booking Updated", "CSW - Room Booking Updated", roomBookingsUpdated, Type.ROOM_BOOKING_UPDATED)
    }

}