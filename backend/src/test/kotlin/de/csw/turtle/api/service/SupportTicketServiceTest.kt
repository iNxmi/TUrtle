package de.csw.turtle.api.service

import de.csw.turtle.api.dto.create.CreateSupportTicketRequest
import de.csw.turtle.api.dto.patch.PatchSupportTicketRequest
import de.csw.turtle.api.entity.SupportTicketEntity
import de.csw.turtle.api.exception.exceptions.crud.CRUDResourceNotFoundException
import de.csw.turtle.api.mapper.SupportTicketMapper
import de.csw.turtle.api.repository.SupportTicketRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import java.util.Optional

@ExtendWith(MockKExtension::class)
class SupportTicketServiceTest {
    @MockK
    lateinit var repository: SupportTicketRepository

    @MockK
    lateinit var mapper: SupportTicketMapper

    @InjectMockKs
    lateinit var supportTicketService: SupportTicketService

    @Test
    fun getAll_withoutRsql_delegatesToRepositoryWithNullSpec() {
        val list = listOf(
            SupportTicketEntity(
                urgency = SupportTicketEntity.Urgency.MEDIUM,
                category = SupportTicketEntity.Category.TECHNICAL,
                email = "KW@example.com",
                subject = "Subject",
                description = "Description"
            )
        )
        val sort = Sort.by(Sort.Order.asc("createdAt"))
        every { repository.findAll(null as Specification<SupportTicketEntity>?, sort) } returns list

        val result = supportTicketService.getAll(sort = sort, rsql = null)

        assertEquals(list, result)
        verify(exactly = 1) { repository.findAll(null as Specification<SupportTicketEntity>?, sort) }
    }

    @Test
    fun getAll_withRsql_delegatesToRepositoryWithSpec() {
        val list = listOf(
            SupportTicketEntity(
                urgency = SupportTicketEntity.Urgency.MEDIUM,
                category = SupportTicketEntity.Category.TECHNICAL,
                email = "KW@example.com",
                subject = "Subject",
                description = "Description"
            )
        )
        every { repository.findAll(any<Specification<SupportTicketEntity>>(), any<Sort>()) } returns list

        val result = supportTicketService.getAll(sort = Sort.unsorted(), rsql = "subject=='S'")

        assertEquals(1, result.size)
        verify(exactly = 1) { repository.findAll(any<Specification<SupportTicketEntity>>(), any<Sort>()) }
    }

    @Test
    fun getPage_withoutRsql_delegatesToRepositoryWithNullSpec() {
        val pageable: Pageable = PageRequest.of(0, 10)
        val list = listOf(
            SupportTicketEntity(
                urgency = SupportTicketEntity.Urgency.MEDIUM,
                category = SupportTicketEntity.Category.TECHNICAL,
                email = "KW@example.com",
                subject = "Subject",
                description = "Description"
            )
        )
        val page = PageImpl(list, pageable, list.size.toLong())
        every { repository.findAll(null as Specification<SupportTicketEntity>?, pageable) } returns page

        val result = supportTicketService.getPage(pageable = pageable, rsql = null)

        assertEquals(1, result.totalElements)
        verify(exactly = 1) { repository.findAll(null as Specification<SupportTicketEntity>?, pageable) }
    }

    @Test
    fun getPage_withRsql_delegatesToRepositoryWithSpec() {
        val pageable: Pageable = PageRequest.of(0, 10)
        val list = listOf(
            SupportTicketEntity(
                urgency = SupportTicketEntity.Urgency.MEDIUM,
                category = SupportTicketEntity.Category.TECHNICAL,
                email = "KW@example.com",
                subject = "Subject",
                description = "Description"
            )
        )
        val page = PageImpl(list, pageable, list.size.toLong())
        every { repository.findAll(any<Specification<SupportTicketEntity>>(), pageable) } returns page

        val result = supportTicketService.getPage(pageable = pageable, rsql = "subject=='Outage'")

        assertEquals(1, result.content.size)
        verify(exactly = 1) { repository.findAll(any<Specification<SupportTicketEntity>>(), pageable) }
    }

    @Test
    fun create_callsMapperAndSaves_returnsSavedEntity() {
        val request = CreateSupportTicketRequest(
            urgency = SupportTicketEntity.Urgency.MEDIUM,
            category = SupportTicketEntity.Category.TECHNICAL,
            email = "KW@example.com",
            subject = "Subject",
            description = "Description"
        )
        val mapped = SupportTicketEntity(
            urgency = request.urgency,
            category = request.category,
            email = request.email,
            subject = request.subject,
            description = request.description
        )
        val saved = SupportTicketEntity(
            urgency = request.urgency,
            category = request.category,
            email = request.email,
            subject = request.subject,
            description = request.description
        )

        every { mapper.create(request) } returns mapped
        every { repository.save(mapped) } returns saved

        val result = supportTicketService.create(request)

        assertEquals(saved, result)
        verify(exactly = 1) { mapper.create(request) }
        verify(exactly = 1) { repository.save(mapped) }
    }

    @Test
    fun getOrNull_whenPresent_returnsEntity() {
        val id = 67L
        val entity = SupportTicketEntity(
            urgency = SupportTicketEntity.Urgency.MEDIUM,
            category = SupportTicketEntity.Category.TECHNICAL,
            email = "KW@example.com",
            subject = "Subject",
            description = "Description"
        )
        every { repository.findById(id) } returns Optional.of(entity)

        val result = supportTicketService.getOrNull(id)

        assertSame(entity, result)
        verify(exactly = 1) { repository.findById(id) }
    }

    @Test
    fun getOrNull_whenMissing_returnsNull() {
        val id = 67L
        every { repository.findById(id) } returns Optional.empty()

        val result = supportTicketService.getOrNull(id)

        assertNull(result)
        verify(exactly = 1) { repository.findById(id) }
    }

    @Test
    fun get_whenPresent_returnsEntity() {
        val id = 67L
        val entity = SupportTicketEntity(
            urgency = SupportTicketEntity.Urgency.MEDIUM,
            category = SupportTicketEntity.Category.TECHNICAL,
            email = "KW@example.com",
            subject = "Subject",
            description = "Description"
        )
        every { repository.findById(id) } returns Optional.of(entity)

        val result = supportTicketService.get(id)

        assertSame(entity, result)
        verify(exactly = 1) { repository.findById(id) }
    }

    @Test
    fun get_whenMissing_throwsCRUDResourceNotFoundException() {
        val id = 67L
        every { repository.findById(id) } returns Optional.empty()

        assertThrows<CRUDResourceNotFoundException> { supportTicketService.get(id) }
        verify(exactly = 1) { repository.findById(id) }
    }

    @Test
    fun patch_updatesEntityAndSaves() {
        val id = 67L
        val existingEntity = SupportTicketEntity(
            urgency = SupportTicketEntity.Urgency.MEDIUM,
            category = SupportTicketEntity.Category.TECHNICAL,
            email = "KW@example.com",
            subject = "Subject",
            description = "Description"
        )
        val request = PatchSupportTicketRequest(
            urgency = SupportTicketEntity.Urgency.HIGH,
            category = null,
            email = null,
            subject = "New",
            description = "New desc",
            status = SupportTicketEntity.Status.IN_PROGRESS
        )
        val updated = SupportTicketEntity(
            urgency = SupportTicketEntity.Urgency.HIGH,
            category = SupportTicketEntity.Category.GENERAL,
            email = "KW@example.com",
            subject = "New",
            description = "New desc",
            status = SupportTicketEntity.Status.IN_PROGRESS
        )

        every { repository.findById(id) } returns Optional.of(existingEntity)
        every { mapper.patch(existingEntity, request) } returns updated
        every { repository.save(updated) } returns updated

        val result = supportTicketService.patch(id, request)

        assertEquals(SupportTicketEntity.Urgency.HIGH, result.urgency)
        assertEquals(SupportTicketEntity.Category.GENERAL, result.category)
        assertEquals("KW@example.com", result.email)
        assertEquals("New", result.subject)
        assertEquals("New desc", result.description)
        assertEquals(SupportTicketEntity.Status.IN_PROGRESS, result.status)
        verify(exactly = 1) { repository.findById(id) }
        verify(exactly = 1) { mapper.patch(existingEntity, request) }
        verify(exactly = 1) { repository.save(updated) }
    }

    @Test
    fun delete_deletesExistingEntity() {
        val id = 67L
        val existingEntity = SupportTicketEntity(
            urgency = SupportTicketEntity.Urgency.MEDIUM,
            category = SupportTicketEntity.Category.TECHNICAL,
            email = "KW@example.com",
            subject = "Subject",
            description = "Description"
        )
        every { repository.findById(id) } returns Optional.of(existingEntity)
        every { repository.delete(existingEntity) } returns Unit

        supportTicketService.delete(id)

        verify(exactly = 1) { repository.findById(id) }
        verify(exactly = 1) { repository.delete(existingEntity) }
    }

    @Test
    fun count_delegatesToRepository() {
        val amount = 42L
        every { repository.count() } returns amount

        val result = supportTicketService.count()

        assertEquals(amount, result)
        verify(exactly = 1) { repository.count() }
    }
}