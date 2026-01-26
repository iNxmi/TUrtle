//package de.csw.turtle.api.service
//
//import de.csw.turtle.api.dto.create.CreateUserRequest
//import de.csw.turtle.api.dto.patch.PatchProfileRequest
//import de.csw.turtle.api.entity.UserEntity
//import de.csw.turtle.api.exception.ConflictException
//import de.csw.turtle.api.exception.NotFoundException
//import de.csw.turtle.api.mapper.UserMapper
//import de.csw.turtle.api.repository.UserRepository
//import io.mockk.MockKAnnotations
//import io.mockk.confirmVerified
//import io.mockk.every
//import io.mockk.impl.annotations.InjectMockKs
//import io.mockk.impl.annotations.MockK
//import io.mockk.junit5.MockKExtension
//import io.mockk.verify
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.assertThrows
//import org.junit.jupiter.api.extension.ExtendWith
//import org.springframework.security.crypto.password.PasswordEncoder
//import kotlin.test.assertEquals
//
//@ExtendWith(MockKExtension::class)
// TODO Disabled for now
//class UserServiceTest {
//
//    @MockK
//    lateinit var repository: UserRepository
//
//    @MockK
//    lateinit var mapper: UserMapper
//
//    @MockK
//    lateinit var encoder: PasswordEncoder
//
//    @InjectMockKs
//    lateinit var service: UserService
//
//    @BeforeEach
//    fun setup() {
//        MockKAnnotations.init(this)
//    }
//
//    private fun sampleUserEntity(): UserEntity = UserEntity(
//        "test", "test", "test", "test", "12345", "test"
//    )
//
//    @Test
//    fun testGetOrNull() {
//        every { repository.findByUsername(any()) } returns null
//        every { repository.findByUsername("test") } returns sampleUserEntity()
//
//        val nullResult = service.getOrNull("invalid username")
//        val result = service.getOrNull("test")
//
//        assertEquals(null, nullResult)
//        assertEquals(sampleUserEntity(), result)
//
//        verify(exactly = 1) { repository.findByUsername("invalid username") }
//        verify(exactly = 1) { repository.findByUsername("test") }
//        confirmVerified(repository)
//
//    }
//
//    @Test
//    fun testGet() {
//        every { repository.findByUsername(any()) } returns null
//        every { repository.findByUsername("test") } returns sampleUserEntity()
//
//        assertThrows<NotFoundException> { service.get("invalid username") }
//
//        val result = service.get("test")
//
//        assertEquals(sampleUserEntity(), result)
//
//        verify(exactly = 1) { repository.findByUsername("test") }
//        verify(exactly = 1) { repository.findByUsername("invalid username") }
//        confirmVerified(repository)
//    }
//
//    @Test
//    fun testCreate() {
//        val request = CreateUserRequest("test", "test", "test", "test", "test", "password")
//        val invalidRequest = CreateUserRequest("taken", "test", "test", "test", "test", "password")
//
//        every { repository.findByUsername(any()) } returns null
//        every { repository.findByUsername(request.username) } returns sampleUserEntity()
//
//        assertThrows<ConflictException> { service.create(invalidRequest) }
//
//        every { encoder.encode(any()) } returns "password"
//
//        assertEquals("password", service.create(request).password)
//
//    }
//
//    @Test
//    fun testUpdateProfile() {
//        val request = PatchProfileRequest("new", "new", "new", "new", password = "new")
//        //Vielleicht Tests für leere Strings schreiben?
//        //Soll mal eine "BadRequestException" werfen
//
//        every { encoder.encode("new") } returns "new"
//        every { service.get("test") } returns sampleUserEntity()
//        every { repository.save(any()) } returns sampleUserEntity()
//
//        service.updateProfile("test", request)
//
//        assertEquals("new", sampleUserEntity().firstName)
//        assertEquals("new", sampleUserEntity().firstName)
//        assertEquals("new", sampleUserEntity().lastName)
//        assertEquals("new", sampleUserEntity().email)
//        assertEquals("new", sampleUserEntity().password)
//
//    }
//
//}