package de.csw.turtle.api.service

import de.csw.turtle.api.dto.create.CreateSupportTicketRequest
import de.csw.turtle.api.entity.SupportTicketEntity
import de.csw.turtle.api.mapper.SupportTicketMapper
import de.csw.turtle.api.repository.SupportTicketRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith


@ExtendWith(MockKExtension::class)

class SupportTicketServiceTest {
    @MockK
    lateinit var repository: SupportTicketRepository

    @MockK
    lateinit var mapper : SupportTicketMapper

    @InjectMockKs
    lateinit var supportTicketService: SupportTicketService

    @Test
    fun saveTicketTest(){
        val request = mockk<CreateSupportTicketRequest>()
        val entity = mockk<SupportTicketEntity>()

        every {mapper.create(request)} returns entity
        every { repository.save(entity) } returns entity

        val result = supportTicketService.create(request)

        assertEquals(result, result)
        verify(exactly = 1) { repository.save(entity) }
    }

}