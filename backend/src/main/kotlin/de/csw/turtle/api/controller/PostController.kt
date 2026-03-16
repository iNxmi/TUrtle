package de.csw.turtle.api.controller

import de.csw.turtle.api.Permission
import de.csw.turtle.api.dto.create.CreatePostRequest
import de.csw.turtle.api.dto.get.GetPostResponse
import de.csw.turtle.api.dto.patch.PatchPostRequest
import de.csw.turtle.api.entity.PostEntity
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.entity.UserEntity.Status
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.service.PostService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.net.URI

private const val ENDPOINT = "/api/posts"

@RestController
@RequestMapping(ENDPOINT)
class PostController(
    private val postService: PostService
) : CreateController<PostEntity, CreatePostRequest, GetPostResponse>,
    GetController<PostEntity, Long, GetPostResponse>,
    PatchController<PostEntity, PatchPostRequest, GetPostResponse>,
    DeleteController<PostEntity> {

    @PostMapping
    override fun create(
        @AuthenticationPrincipal user: UserEntity?,

        @RequestBody request: CreatePostRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetPostResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (user.status != Status.ACTIVE || !user.hasPermission(Permission.MANAGE_POSTS))
            throw HttpException.Forbidden()

        val entity = postService.create(
            name = request.name,
            description = request.description,
            title = request.title,
            content = request.content,
            enabled = request.enabled
        )

        val location = URI.create("$ENDPOINT/${entity.id}")
        val dto = GetPostResponse(entity)
        return ResponseEntity.created(location).body(dto)
    }

    @GetMapping("/{variable}")
    override fun get(
        @AuthenticationPrincipal user: UserEntity?,

        @PathVariable variable: Long,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetPostResponse> {
        val entity = postService.getById(variable)
            ?: throw HttpException.NotFound()

        if (!entity.enabled) {
            if (user == null)
                throw HttpException.Unauthorized()

            if(user.status != Status.ACTIVE || !user.hasPermission(Permission.MANAGE_POSTS))
                throw HttpException.Forbidden()
        }

        val dto = GetPostResponse(entity)
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
        val specification = if (user == null || user.status != Status.ACTIVE || !user.hasPermission(Permission.MANAGE_POSTS)) {
            Specification { root, _, builder ->
                builder.equal(root.get<Boolean>("enabled"), true)
            }
        } else Specification.unrestricted<PostEntity>()

        val sort = sortProperty?.let {
            Sort.by(sortDirection, sortProperty)
        } ?: Sort.unsorted()

        if (pageNumber != null) {
            val pageable = PageRequest.of(pageNumber, pageSize, sort)
            val page = postService.getPage(rsql = rsql, pageable = pageable, specification = specification)
            val dto = page.map { GetPostResponse(it) }
            return ResponseEntity.ok(dto)
        }

        val collection = postService.getAll(rsql = rsql, sort = sort, specification = specification).toMutableSet()
        val dto = collection.map { GetPostResponse(it) }
        return ResponseEntity.ok(dto)
    }

    @PatchMapping("/{id}")
    override fun patch(
        @AuthenticationPrincipal user: UserEntity?,

        @PathVariable id: Long,
        @RequestBody request: PatchPostRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetPostResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (user.status != Status.ACTIVE || !user.hasPermission(Permission.MANAGE_POSTS))
            throw HttpException.Forbidden()

        val entity = postService.patch(
            id = id,
            name = request.name,
            description = request.description,
            title = request.title,
            content = request.content,
            enabled = request.enabled
        )

        val dto = GetPostResponse(entity)
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

        if (user.status != Status.ACTIVE || !user.hasPermission(Permission.MANAGE_POSTS))
            throw HttpException.Forbidden()

        postService.delete(id)
        return ResponseEntity.noContent().build()
    }

}