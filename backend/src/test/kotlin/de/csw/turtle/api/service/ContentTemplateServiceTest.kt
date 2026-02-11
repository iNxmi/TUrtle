package de.csw.turtle.api.service

import de.csw.turtle.api.dto.create.CreateContentTemplateRequest
import de.csw.turtle.api.dto.patch.PatchContentTemplateRequest
import de.csw.turtle.api.entity.ContentTemplateEntity
import de.csw.turtle.api.exception.NotFoundException
import de.csw.turtle.api.mapper.TemplateMapper
import de.csw.turtle.api.repository.ContentTemplateRepository
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
class ContentTemplateServiceTest {

    @MockK
    lateinit var repository: ContentTemplateRepository

    @MockK
    lateinit var mapper: TemplateMapper

    @InjectMockKs
    lateinit var contentTemplateService: ContentTemplateService

    @Test
    fun getByName_found_returnsEntity() {
        val request = "test"
        val entity = ContentTemplateEntity(request, "description", "content")
        every { repository.findByName(request) } returns entity

        val result = contentTemplateService.getByName(request)

        assertEquals(request, result.name)
        verify(exactly = 1) { repository.findByName(request) }
    }

    @Test
    fun getByName_notFound_throwsAnError_TBI() {
        // TODO
    }

    @Test
    fun getAll_withoutRsql_delegatesToRepositoryWithNullSpec() {
        val list = listOf(ContentTemplateEntity(name = "name", description = "description", content = "content"))
        val sort = Sort.by(Sort.Order.asc("name"))
        every { repository.findAll(null as Specification<ContentTemplateEntity>?, sort) } returns list

        val result = contentTemplateService.getAll(sort = sort, rsql = null)

        assertEquals(list, result)
        verify(exactly = 1) { repository.findAll(null as Specification<ContentTemplateEntity>?, sort) }
    }

    @Test
    fun getAll_withRsql_delegatesToRepositoryWithSpec() {
        val list = listOf(ContentTemplateEntity(name = "name", description = "description", content = "content"))
        every { repository.findAll(any<Specification<ContentTemplateEntity>>(), any<Sort>()) } returns list

        val result = contentTemplateService.getAll(sort = Sort.unsorted(), rsql = "name=='a'")

        assertEquals(1, result.size)
        verify(exactly = 1) { repository.findAll(any<Specification<ContentTemplateEntity>>(), any<Sort>()) }
    }

    @Test
    fun getPage_withoutRsql_delegatesToRepositoryWithNullSpec() {
        val pageable: Pageable = PageRequest.of(0, 10)
        val list = listOf(ContentTemplateEntity(name = "name", description = "description", content = "content"))
        val page = PageImpl(list, pageable, list.size.toLong())
        every { repository.findAll(null as Specification<ContentTemplateEntity>?, pageable) } returns page

        val result = contentTemplateService.getPage(pageable = pageable, rsql = null)

        assertEquals(1, result.totalElements)
        verify(exactly = 1) { repository.findAll(null as Specification<ContentTemplateEntity>?, pageable) }
    }

    @Test
    fun getPage_withRsql_delegatesToRepositoryWithSpec() {
        val pageable: Pageable = PageRequest.of(0, 10)
        val list = listOf(ContentTemplateEntity(name = "name", description = "description", content = "content"))
        val page = PageImpl(list, pageable, list.size.toLong())
        every { repository.findAll(any<Specification<ContentTemplateEntity>>(), pageable) } returns page

        val result = contentTemplateService.getPage(pageable = pageable, rsql = "name=='b'")

        assertEquals(1, result.content.size)
        verify(exactly = 1) { repository.findAll(any<Specification<ContentTemplateEntity>>(), pageable) }
    }

    @Test
    fun create_callsMapperAndSaves_returnsSavedEntity() {
        val request = CreateContentTemplateRequest(name = "name", description = "description", content = "content")
        val mapped = ContentTemplateEntity(name = "name", description = "description", content = "content")
        val saved = ContentTemplateEntity(name = "name", description = "description", content = "content")

        every { mapper.create(request) } returns mapped
        every { repository.save(mapped) } returns saved

        val result = contentTemplateService.create(request)

        assertEquals(saved, result)
        verify(exactly = 1) { mapper.create(request) }
        verify(exactly = 1) { repository.save(mapped) }
    }

    @Test
    fun getOrNull_whenPresent_returnsEntity() {
        val id = 67L
        val entity = ContentTemplateEntity(name = "name", description = "description", content = "content")
        every { repository.findById(id) } returns Optional.of(entity)

        val result = contentTemplateService.getOrNull(id)

        assertSame(entity, result)
        verify(exactly = 1) { repository.findById(id) }
    }

    @Test
    fun getOrNull_whenMissing_returnsNull() {
        val id = 67L
        every { repository.findById(id) } returns Optional.empty()

        val result = contentTemplateService.getOrNull(id)

        assertNull(result)
        verify(exactly = 1) { repository.findById(id) }
    }

    @Test
    fun get_whenPresent_returnsEntity() {
        val id = 67L
        val entity = ContentTemplateEntity(name = "name", description = "description", content = "content")
        every { repository.findById(id) } returns Optional.of(entity)

        val result = contentTemplateService.get(id)

        assertSame(entity, result)
        verify(exactly = 1) { repository.findById(id) }
    }

    @Test
    fun get_whenMissing_throwsNotFoundException() {
        val id = 67L
        every { repository.findById(id) } returns Optional.empty()

        assertThrows<NotFoundException> { contentTemplateService.get(id) }
        verify(exactly = 1) { repository.findById(id) }
    }

    @Test
    fun patch_updatesEntityAndSaves() {
        val id = 67L
        val existingEntity = ContentTemplateEntity("old_name", "old_description", "old_content")
        val request = PatchContentTemplateRequest(name = "new_name", description = null, content = "new_content")
        val updated = ContentTemplateEntity("new_name", "old_description", "new_content")

        every { repository.findById(id) } returns Optional.of(existingEntity)
        every { mapper.patch(existingEntity, request) } returns updated
        every { repository.save(updated) } returns updated

        val result = contentTemplateService.patch(id, request)

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
        val existingEntity = ContentTemplateEntity("name", "description", "content")
        every { repository.findById(id) } returns Optional.of(existingEntity)
        every { repository.delete(existingEntity) } returns Unit

        contentTemplateService.delete(id)

        verify(exactly = 1) { repository.findById(id) }
        verify(exactly = 1) { repository.delete(existingEntity) }
    }

    @Test
    fun count_delegatesToRepository() {
        val amount = 67L
        every { repository.count() } returns amount

        val result = contentTemplateService.count()

        assertEquals(amount, result)
        verify(exactly = 1) { repository.count() }
    }
}