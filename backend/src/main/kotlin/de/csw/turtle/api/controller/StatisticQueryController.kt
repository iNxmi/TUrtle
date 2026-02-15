package de.csw.turtle.api.controller

import de.csw.turtle.api.Permission
import de.csw.turtle.api.dto.create.CreateStatisticQueryRequest
import de.csw.turtle.api.dto.get.GetStatisticQueryResponse
import de.csw.turtle.api.dto.patch.PatchStatisticQueryRequest
import de.csw.turtle.api.entity.StatisticQueryEntity
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.service.StatisticQueryService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.net.URI

private const val ENDPOINT = "/api/statistic-queries"

@RestController
@RequestMapping(ENDPOINT)
class StatisticQueryController(
    private val statisticQueryService: StatisticQueryService
) : CreateController<StatisticQueryEntity, CreateStatisticQueryRequest, GetStatisticQueryResponse>,
    GetController<StatisticQueryEntity, Long, GetStatisticQueryResponse>,
    PatchController<StatisticQueryEntity, PatchStatisticQueryRequest, GetStatisticQueryResponse>,
    DeleteController<StatisticQueryEntity> {

    @PostMapping
    override fun create(
        @AuthenticationPrincipal user: UserEntity?,

        @RequestBody request: CreateStatisticQueryRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetStatisticQueryResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_STATISTIC_QUERIES))
            throw HttpException.Forbidden()

        val entity = statisticQueryService.create(
            name = request.name,
            description = request.description,
            query = request.query
        )

        val location = URI.create("$ENDPOINT/${entity.id}")
        val dto = GetStatisticQueryResponse(entity)
        return ResponseEntity.created(location).body(dto)
    }

    @GetMapping("/{variable}")
    override fun get(
        @AuthenticationPrincipal user: UserEntity?,

        @PathVariable variable: Long,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetStatisticQueryResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_STATISTIC_QUERIES))
            throw HttpException.Forbidden()

        val entity = statisticQueryService.getById(variable)
            ?: throw HttpException.NotFound()

        val dto = GetStatisticQueryResponse(entity)
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
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_STATISTIC_QUERIES))
            throw HttpException.Forbidden()

        val sort = sortProperty?.let {
            Sort.by(sortDirection, sortProperty)
        } ?: Sort.unsorted()

        if (pageNumber != null) {
            val pageable = PageRequest.of(pageNumber, pageSize, sort)
            val page = statisticQueryService.getPage(rsql = rsql, pageable = pageable)
            val dto = page.map { GetStatisticQueryResponse(it) }
            return ResponseEntity.ok(dto)
        }

        val collection = statisticQueryService.getAll(rsql = rsql, sort = sort).toMutableSet()
        val dto = collection.map { GetStatisticQueryResponse(it) }
        return ResponseEntity.ok(dto)
    }

    @PatchMapping("/{id}")
    override fun patch(
        @AuthenticationPrincipal user: UserEntity?,

        @PathVariable id: Long,
        @RequestBody request: PatchStatisticQueryRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetStatisticQueryResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_STATISTIC_QUERIES))
            throw HttpException.Forbidden()

        val entity = statisticQueryService.patch(
            id = id,
            name = request.name,
            description = request.description,
            query = request.query
        )

        val dto = GetStatisticQueryResponse(entity)
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

        if (!user.hasPermission(Permission.MANAGE_STATISTIC_QUERIES))
            throw HttpException.Forbidden()

        if (!statisticQueryService.existsById(id))
            throw HttpException.NotFound()

        statisticQueryService.delete(id)

        return ResponseEntity.noContent().build()
    }

}