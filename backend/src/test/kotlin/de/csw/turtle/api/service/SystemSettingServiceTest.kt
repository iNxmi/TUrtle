package de.csw.turtle.api.service

import de.csw.turtle.api.entity.SystemSettingEntity
import de.csw.turtle.api.mapper.SystemSettingMapper
import de.csw.turtle.api.mapper.UserMapper
import de.csw.turtle.api.repository.SystemSettingRepository
import de.csw.turtle.api.repository.UserRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNull
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
class SystemSettingServiceTest {

    @MockK
    lateinit var repository: SystemSettingRepository

    @MockK
    lateinit var mapper: SystemSettingMapper

    @InjectMockKs
    lateinit var service: SystemSettingService

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun getKey_Returns_Correct_Value() {
        val key = "KW"
        val setting = SystemSettingEntity("KW", "FOPBOT");

        every { repository.findByKey(any()) } returns null
        every { repository.findByKey(key) } returns setting

        val result = service.getByKeyOrNull("KW")

        assertEquals(setting.value, result?.value )

        verify(exactly = 1) { repository.findByKey(key) }

    }
    @Test
    fun getKey_Returns_Null() {
        val key = "KW"
        val value = "FOPBOT"
        val setting = SystemSettingEntity(key, value);

        every { repository.findByKey(any()) } returns null
        every { repository.findByKey(key) } returns setting

        val result = service.getByKeyOrNull("Babana")

        assertNull(result?.value)
    }
    @Test
    fun getValueOrDefault_Returns_Value() {
        val key = "KW"
        val value = "FOPBOT"
        val setting = SystemSettingEntity(key, value);

        every { repository.findByKey(any()) } returns null
        every { repository.findByKey(key) } returns setting

        val result = service.getValueOrDefault(key, "Babana")

        assertEquals(setting.value, result)
    }
    @Test
    fun getValueOrDefault_Returns_Default() {
        val key = "KW"
        val value = "FOPBOT"
        val setting = SystemSettingEntity(key, value);

        every { repository.findByKey(any()) } returns null
        every { repository.findByKey(key) } returns setting

        val result = service.getValueOrDefault(key, "Babana")

        assertEquals("Babana", result)
    }
    @Test
    fun getValueOrNull_Returns_Null() {
        val key = "KW"
        val value = "FOPBOT"
        val setting = SystemSettingEntity(key, value);

        every { repository.findByKey(any()) } returns null
        every { repository.findByKey(key) } returns setting

        val result = service.getValueOrNull("Definitiv nicht KW")

        assertNull(result)
    }
    @Test
    fun getValueOrNull_Returns_Value() {
        val key = "KW"
        val value = "FOPBOT"
        val setting = SystemSettingEntity(key, value);

        every { repository.findByKey(any()) } returns null
        every { repository.findByKey(key) } returns setting

        val result = service.getValueOrNull(key)

        assertEquals(setting.value, result)
    }


}