package de.csw.turtle.api.service

import de.csw.turtle.api.mapper.TemplateMapper
import de.csw.turtle.api.mapper.UserMapper
import de.csw.turtle.api.repository.UserRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.crypto.password.PasswordEncoder

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
    fun test(){

    }

}