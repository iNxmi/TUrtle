package de.csw.turtle.api.controller

import de.csw.turtle.api.Permission
import de.csw.turtle.api.dto.get.GetTokenResponse
import de.csw.turtle.api.entity.TokenEntity
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.service.TokenService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/token")
class TokenController(
    private val tokenService: TokenService
) : GetController<TokenEntity, String, GetTokenResponse> {

    @GetMapping("/{variable}")
    override fun get(
        @AuthenticationPrincipal user: UserEntity?,

        @PathVariable variable: String,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetTokenResponse> {
        val id = variable.toLongOrNull()
        if (id != null) {
            if (user == null)
                throw HttpException.Unauthorized()

            if (!user.hasPermission(Permission.MANAGE_TOKENS))
                throw HttpException.Forbidden()
        }

        val entity = if (id != null) {
            tokenService.getById(id)
        } else {
            tokenService.getByUuid(variable)
        } ?: throw HttpException.NotFound()

        val dto = GetTokenResponse(entity)
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

        if (!user.hasPermission(Permission.MANAGE_TOKENS))
            throw HttpException.Forbidden()

        val sort = sortProperty?.let {
            Sort.by(sortDirection, sortProperty)
        } ?: Sort.unsorted()

        if (pageNumber != null) {
            val pageable = PageRequest.of(pageNumber, pageSize, sort)
            val page = tokenService.getPage(rsql = rsql, pageable = pageable)
            val dto = page.map { GetTokenResponse(it) }
            return ResponseEntity.ok(dto)
        }

        val collection = tokenService.getAll(rsql = rsql, sort = sort).toMutableSet()
        val dto = collection.map { GetTokenResponse(it) }
        return ResponseEntity.ok(dto)
    }

}