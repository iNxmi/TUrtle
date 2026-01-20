package de.csw.turtle.api.service

import de.csw.turtle.api.dto.create.CreateTemplateRequest
import de.csw.turtle.api.dto.patch.PatchTemplateRequest
import de.csw.turtle.api.entity.TemplateEntity
import de.csw.turtle.api.exception.NotFoundException
import de.csw.turtle.api.mapper.TemplateMapper
import de.csw.turtle.api.repository.TemplateRepository
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
import java.util.Optional
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification


@ExtendWith(MockKExtension::class)
class TemplateServiceTest {

    @MockK
    lateinit var repository: TemplateRepository

    @MockK
    lateinit var mapper: TemplateMapper

    @InjectMockKs
    lateinit var templateService: TemplateService

    @Test
    fun getByName_found_returnsEntity() {
        val request = "test"
        val entity = TemplateEntity(request, "description", "content")
        every { repository.findByName(request) } returns entity

        val result = templateService.getByName(request)

        assertEquals(request, result.name)
        verify(exactly = 1) { repository.findByName(request) }
    }

    @Test
    fun getByName_notFound_throwsAnError_TBI() {
        // TODO
    }

    @Test
    fun getAll_withoutRsql_delegatesToRepositoryWithNullSpec() {
        val list = listOf(TemplateEntity(name = "name", description = "description", content = "content"))
        val sort = Sort.by(Sort.Order.asc("name"))
        every { repository.findAll(null as Specification<TemplateEntity>?, sort) } returns list

        val result = templateService.getAll(sort = sort, rsql = null)

        assertEquals(list, result)
        verify(exactly = 1) { repository.findAll(null as Specification<TemplateEntity>?, sort) }
    }

    @Test
    fun getAll_withRsql_delegatesToRepositoryWithSpec() {
        val list = listOf(TemplateEntity(name = "name", description = "description", content = "content"))
        every { repository.findAll(any<Specification<TemplateEntity>>(), any<Sort>()) } returns list

        val result = templateService.getAll(sort = Sort.unsorted(), rsql = "name=='a'")

        assertEquals(1, result.size)
        verify(exactly = 1) { repository.findAll(any<Specification<TemplateEntity>>(), any<Sort>()) }
    }

    @Test
    fun getPage_withoutRsql_delegatesToRepositoryWithNullSpec() {
        val pageable: Pageable = PageRequest.of(0, 10)
        val list = listOf(TemplateEntity(name = "name", description = "description", content = "content"))
        val page = PageImpl(list, pageable, list.size.toLong())
        every { repository.findAll(null as Specification<TemplateEntity>?, pageable) } returns page

        val result = templateService.getPage(pageable = pageable, rsql = null)

        assertEquals(1, result.totalElements)
        verify(exactly = 1) { repository.findAll(null as Specification<TemplateEntity>?, pageable) }
    }

    @Test
    fun getPage_withRsql_delegatesToRepositoryWithSpec() {
        val pageable: Pageable = PageRequest.of(0, 10)
        val list = listOf(TemplateEntity(name = "name", description = "description", content = "content"))
        val page = PageImpl(list, pageable, list.size.toLong())
        every { repository.findAll(any<Specification<TemplateEntity>>(), pageable) } returns page

        val result = templateService.getPage(pageable = pageable, rsql = "name=='b'")

        assertEquals(1, result.content.size)
        verify(exactly = 1) { repository.findAll(any<Specification<TemplateEntity>>(), pageable) }
    }

    @Test
    fun create_callsMapperAndSaves_returnsSavedEntity() {
        val request = CreateTemplateRequest(name = "name", description = "description", content = "content")
        val mapped = TemplateEntity(name = "name", description = "description", content = "content")
        val saved = TemplateEntity(name = "name", description = "description", content = "content")

        every { mapper.create(request) } returns mapped
        every { repository.save(mapped) } returns saved

        val result = templateService.create(request)

        assertEquals(saved, result)
        verify(exactly = 1) { mapper.create(request) }
        verify(exactly = 1) { repository.save(mapped) }
    }

    @Test
    fun getOrNull_whenPresent_returnsEntity() {
        val id = 67L
        val entity = TemplateEntity(name = "name", description = "description", content = "content")
        every { repository.findById(id) } returns Optional.of(entity)

        val result = templateService.getOrNull(id)

        assertSame(entity, result)
        verify(exactly = 1) { repository.findById(id) }
    }

    @Test
    fun getOrNull_whenMissing_returnsNull() {
        val id = 67L
        every { repository.findById(id) } returns Optional.empty()

        val result = templateService.getOrNull(id)

        assertNull(result)
        verify(exactly = 1) { repository.findById(id) }
    }

    @Test
    fun get_whenPresent_returnsEntity() {
        val id = 67L
        val entity = TemplateEntity(name = "name", description = "description", content = "content")
        every { repository.findById(id) } returns Optional.of(entity)

        val result = templateService.get(id)

        assertSame(entity, result)
        verify(exactly = 1) { repository.findById(id) }
    }

    @Test
    fun get_whenMissing_throwsNotFoundException() {
        val id = 67L
        every { repository.findById(id) } returns Optional.empty()

        assertThrows<NotFoundException> { templateService.get(id) }
        verify(exactly = 1) { repository.findById(id) }
    }

    @Test
    fun patch_updatesEntityAndSaves() {
        val id = 67L
        val existingEntity = TemplateEntity("old_name", "old_description", "old_content")
        val request = PatchTemplateRequest(name = "new_name", description = null, content = "new_content")
        val updated = TemplateEntity("new_name", "old_description", "new_content")

        every { repository.findById(id) } returns Optional.of(existingEntity)
        every { mapper.patch(existingEntity, request) } returns updated
        every { repository.save(updated) } returns updated

        val result = templateService.patch(id, request)

        assertEquals("new_name", result.name)
        assertEquals("old_description", result.description)
        assertEquals("new_content", result.content)
        verify(exactly = 1) { repository.findById(id) }
        verify(exactly = 1) { mapper.patch(existingEntity, request) }
        verify(exactly = 1) { repository.save(updated) }
    }

    @Test
    fun delete_deletesExistingEntity() {
        val id = 67L
        val existingEntity = TemplateEntity("name", "description", "content")
        every { repository.findById(id) } returns Optional.of(existingEntity)
        every { repository.delete(existingEntity) } returns Unit

        templateService.delete(id)

        verify(exactly = 1) { repository.findById(id) }
        verify(exactly = 1) { repository.delete(existingEntity) }
    }

    @Test
    fun count_delegatesToRepository() {
        val amount = 67L
        every { repository.count() } returns amount

        val result = templateService.count()

        assertEquals(amount, result)
        verify(exactly = 1) { repository.count() }
    }
}