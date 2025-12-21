package de.csw.turtle.api.config.defaults

import de.csw.turtle.api.Permission
import de.csw.turtle.api.Permission.*
import de.csw.turtle.api.dto.create.CreateRoleRequest
import de.csw.turtle.api.service.RoleService
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.transaction.annotation.Transactional

@Order(1)
@Configuration
class DefaultRolesConfiguration(
    private val service: RoleService
) : CommandLineRunner {

    private val guest = setOf(
        FRONTEND__SIDEBAR_CATEGORY__PUBLIC,
        FRONTEND__SIDEBAR_ITEM__HOME,
        FRONTEND__SIDEBAR_ITEM__LOGIN,
        FRONTEND__SIDEBAR_ITEM__REGISTER,
        FRONTEND__SIDEBAR_ITEM__SUPPORT,
        FRONTEND__SIDEBAR_ITEM__FAQ,
        FRONTEND__SIDEBAR_ITEM__ABOUT
    )

    private val student = setOf(
        BACKEND__API_USER_PROFILE__GET,
        BACKEND__API_USER_PROFILE__PATCH,
        BACKEND__API_USER_PROFILE__DELETE,

        FRONTEND__SIDEBAR_CATEGORY__PUBLIC,
        FRONTEND__SIDEBAR_ITEM__HOME,
        FRONTEND__SIDEBAR_ITEM__SUPPORT,
        FRONTEND__SIDEBAR_ITEM__FAQ,
        FRONTEND__SIDEBAR_ITEM__ABOUT,

        FRONTEND__SIDEBAR_CATEGORY__USER,
        FRONTEND__SIDEBAR_ITEM__DASHBOARD,
        FRONTEND__SIDEBAR_ITEM__PROFILE,
        FRONTEND__SIDEBAR_ITEM__BOOK_DEVICE
    )

    private val professor = setOf(
        BACKEND__API_USER_PROFILE__GET,
        BACKEND__API_USER_PROFILE__PATCH,
        BACKEND__API_USER_PROFILE__DELETE,

        FRONTEND__SIDEBAR_CATEGORY__PUBLIC,
        FRONTEND__SIDEBAR_ITEM__HOME,
        FRONTEND__SIDEBAR_ITEM__SUPPORT,
        FRONTEND__SIDEBAR_ITEM__FAQ,
        FRONTEND__SIDEBAR_ITEM__ABOUT,

        FRONTEND__SIDEBAR_CATEGORY__USER,
        FRONTEND__SIDEBAR_ITEM__DASHBOARD,
        FRONTEND__SIDEBAR_ITEM__PROFILE,
        FRONTEND__SIDEBAR_ITEM__BOOK_DEVICE,
        FRONTEND__SIDEBAR_ITEM__BOOK_ROOM,
    )

    private val administrator = Permission.entries.toSet()

    @Transactional
    override fun run(vararg args: String) {
        if (service.count() > 0)
            return

        service.create(CreateRoleRequest(name = "Guest", permissions = guest))
        service.create(CreateRoleRequest(name = "Student", permissions = student))
        service.create(CreateRoleRequest(name = "Professor", permissions = professor))
        service.create(CreateRoleRequest(name = "Administrator", permissions = administrator))
    }

}