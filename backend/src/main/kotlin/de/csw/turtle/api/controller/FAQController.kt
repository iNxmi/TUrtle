package de.csw.turtle.api.controller

import de.csw.turtle.api.Permission
import de.csw.turtle.api.dto.create.CreateFAQRequest
import de.csw.turtle.api.dto.get.GetFAQResponse
import de.csw.turtle.api.dto.patch.PatchFAQRequest
import de.csw.turtle.api.entity.FAQEntity
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.service.FAQService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.net.URI

private const val ENDPOINT = "/api/faq"

@RestController
@RequestMapping(ENDPOINT)
class FAQController(
    private val faqService: FAQService
) : CreateController<FAQEntity, CreateFAQRequest, GetFAQResponse>,
    GetController<FAQEntity, Long, GetFAQResponse>,
    PatchController<FAQEntity, PatchFAQRequest, GetFAQResponse>,
    DeleteController<FAQEntity> {

    @PostMapping
    override fun create(
        @AuthenticationPrincipal user: UserEntity?,

        @RequestBody request: CreateFAQRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetFAQResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_FAQ))
            throw HttpException.Forbidden()

        val entity = faqService.create(
            name = request.name,
            title = request.title,
            content = request.content,
            enabled = request.enabled
        )

        val location = URI.create("$ENDPOINT/${entity.id}")
        val dto = GetFAQResponse(entity)
        return ResponseEntity.created(location).body(dto)
    }

    @GetMapping("/{variable}")
    override fun get(
        @AuthenticationPrincipal user: UserEntity?,

        @PathVariable variable: Long,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetFAQResponse> {
        val entity = faqService.getById(variable)
            ?: throw HttpException.NotFound()

        if (!entity.enabled)
            if (user == null || !user.hasPermission(Permission.MANAGE_FAQ))
                throw HttpException.Unauthorized()

        val dto = GetFAQResponse(entity)
        return ResponseEntity.ok(dto)
    }

    @GetMapping
    override fun getCollection(
        @AuthenticationPrincipal user: UserEntity?,

        @RequestParam rsql: String?,
        @RequestParam pageNumber: Int?,
        @RequestParam pageSize: Int,
        @RequestParam sortProperty: String?,
        @RequestParam sortDirection: Sort.Direction,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<Any> {
        val specification = if (user == null || !user.hasPermission(Permission.MANAGE_FAQ)) {
            Specification { root, _, builder ->
                builder.equal(root.get<Boolean>("enabled"), true)
            }
        } else Specification.unrestricted<FAQEntity>()

        val sort = sortProperty?.let {
            Sort.by(sortDirection, sortProperty)
        } ?: Sort.unsorted()

        if (pageNumber != null) {
            val pageable = PageRequest.of(pageNumber, pageSize, sort)
            val page = faqService.getPage(rsql = rsql, pageable = pageable, specification = specification)
            val dto = page.map { GetFAQResponse(it) }
            return ResponseEntity.ok(dto)
        }

        val collection = faqService.getAll(rsql = rsql, sort = sort, specification = specification).toMutableSet()
        val dto = collection.map { GetFAQResponse(it) }
        return ResponseEntity.ok(dto)
    }

    @PatchMapping("/{id}")
    override fun patch(
        @AuthenticationPrincipal user: UserEntity?,

        @PathVariable id: Long,
        @RequestBody request: PatchFAQRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetFAQResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_FAQ))
            throw HttpException.Forbidden()

        val entity = faqService.patch(
            id = id,
            name = request.name,
            title = request.title,
            content = request.content,
            enabled = request.enabled
        )

        val dto = GetFAQResponse(entity)
        return ResponseEntity.ok(dto)
    }

    @DeleteMapping("/{id}")
    override fun delete(
        @AuthenticationPrincipal user: UserEntity?,

        @PathVariable id: Long,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<Void> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_FAQ))
            throw HttpException.Forbidden()

        faqService.delete(id)
        return ResponseEntity.noContent().build()
    }

}