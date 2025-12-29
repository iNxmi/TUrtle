package de.csw.turtle
import de.csw.turtle.api.controller.api.UserController
import de.csw.turtle.api.mapper.UserMapper
import de.csw.turtle.api.service.PermissionService
import de.csw.turtle.api.service.UserService
import io.mockk.mockk
import io.mockk.every
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class UserControllerTest {

    private val userService = mockk<UserService>()
    private val userMapper = mockk<UserMapper>()
    private val userPermissionService = mockk<PermissionService>()

    private val userController = UserController(service = userService, mapper = userMapper, permissionService = userPermissionService)



}