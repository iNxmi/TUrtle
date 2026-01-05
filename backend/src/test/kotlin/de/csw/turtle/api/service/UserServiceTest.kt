package de.csw.turtle.api.service

import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.exceptions.user.UserNotFoundException
import de.csw.turtle.api.mapper.TemplateMapper
import de.csw.turtle.api.mapper.UserMapper
import de.csw.turtle.api.repository.UserRepository
import io.mockk.MockKAnnotations
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.crypto.password.PasswordEncoder
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
class UserServiceTest {

    @MockK
    lateinit var repository: UserRepository

    @MockK
    lateinit var mapper: UserMapper

    @MockK
    lateinit var encoder: PasswordEncoder

    @InjectMockKs
    lateinit var service: UserService

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun testGetOrNull(){
        val entity = UserEntity("test","test","test","test","test")

        every { repository.findByUsername(any()) } returns null
        every { repository.findByUsername("test") } returns entity

        val nullResult = service.getOrNull("invalid username")
        val result = service.getOrNull("test")

        assertEquals(null, nullResult)
        assertEquals(entity, result)

        verify(exactly = 1) { repository.findByUsername("invalid username") }
        verify(exactly = 1) { repository.findByUsername("test") }
        confirmVerified(repository)

    }

    @Test
    fun testGet(){
        val entity = UserEntity("test","test","test","test","test")

        every { repository.findByUsername(any()) } returns null
        every { repository.findByUsername("test") } returns entity

        assertThrows<UserNotFoundException> { service.get("invalid username") }

        val result = service.get("test")

        assertEquals(entity, result)

        verify(exactly = 1) { repository.findByUsername("test") }
        verify(exactly = 1) { repository.findByUsername("invalid username") }
        confirmVerified(repository)
    }

    @Test
    fun testCreate(){

    }

    @Test
    fun testPatch(){

    }

    @Test
    fun testUpdateProfile(){

    }

}