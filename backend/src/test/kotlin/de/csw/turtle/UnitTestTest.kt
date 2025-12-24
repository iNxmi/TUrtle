package de.csw.turtle
import de.csw.turtle.api.dto.create.CreateUserRequest
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.exceptions.user.UsernameAlreadyExistsException
import de.csw.turtle.api.mapper.UserMapper
import de.csw.turtle.api.repository.UserRepository
import de.csw.turtle.api.service.UserService
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.crypto.password.PasswordEncoder

@ExtendWith(MockKExtension::class)

class UnitTestTest {
    @MockK
    lateinit var repository: UserRepository

    @MockK
    lateinit var mapper : UserMapper

    @MockK
    lateinit var passwordEncoder : PasswordEncoder

    @InjectMockKs
    lateinit var userService : UserService

    @Test
    fun userNameTest(){
        val request = CreateUserRequest(
            username = "Joe",
            firstName = "Joe",
            lastName = "Buyden",
            email = "joe@buyden.usa",
            password = "trump"
        )

        every { repository.findByUsername("Joe") } returns mockk<UserEntity>()

        // assertNotNull(userService.get(request.username))
        assertThrows<UsernameAlreadyExistsException>{
            userService.create(request)
        }
    }
}