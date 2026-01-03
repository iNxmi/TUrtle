package de.csw.turtle.api.service

import de.csw.turtle.api.controller.api.UserController
import de.csw.turtle.api.entity.TemplateEntity
import de.csw.turtle.api.mapper.TemplateMapper
import de.csw.turtle.api.repository.TemplateRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class TemplateServiceTest {

    @MockK
    lateinit var repository : TemplateRepository

    @MockK
    lateinit var mapper : TemplateMapper

    @InjectMockKs
    lateinit var templateService: TemplateService

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun getByNameTest(){

        val name: String = "test"

        every{repository.findByName("test")} returns TemplateEntity("test", "test", "test")

        val result = templateService.getByName(name)

        assertEquals("test", result.name)

    }






}