package de.csw.turtle.api.controller

import de.csw.turtle.api.Permission
import de.csw.turtle.api.dto.create.CreateUserRequest
import de.csw.turtle.api.dto.get.GetUserResponse
import de.csw.turtle.api.dto.patch.PatchUserRequest
import de.csw.turtle.api.entity.RoleEntity.Type
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.entity.UserEntity.Status
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.service.AltchaService
import de.csw.turtle.api.service.NetworkService
import de.csw.turtle.api.service.RoleService
import de.csw.turtle.api.service.UserService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.net.URI

private const val ENDPOINT = "/api/users"

@RestController
@RequestMapping(ENDPOINT)
class UserController(
    private val userService: UserService,
    private val altchaService: AltchaService,
    private val networkService: NetworkService,
    private val roleService: RoleService
) : CreateController<UserEntity, CreateUserRequest, GetUserResponse>,
    GetController<UserEntity, String, GetUserResponse>,
    PatchController<UserEntity, PatchUserRequest, GetUserResponse>,
    DeleteController<UserEntity> {

    @PostMapping
    override fun create(
        @AuthenticationPrincipal user: UserEntity?,

        @RequestBody request: CreateUserRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetUserResponse> {
        if (user == null) {
            val ipAddress = networkService.getClientIp(httpRequest)
            if (!altchaService.isTrusted(ipAddress))
                if (request.altchaToken == null || !altchaService.isValid(request.altchaToken))
                    throw HttpException.Forbidden("Invalid captcha token.")
        } else if (!user.hasPermission(Permission.MANAGE_USERS))
            throw HttpException.Forbidden()

        val role = roleService.getByType(Type.STUDENT)
            ?: throw HttpException.InternalServerError()

        var emojis = userService.generateEmojis()
        var status = Status.PENDING_VERIFICATION
        var roleIds = setOf(role.id)
        if (user != null) {
            request.emojis?.let { emojis = it }
            request.status?.let { status = it }
            request.roleIds?.let { roleIds = it }
        }

        val entity = userService.create(
            username = request.username,
            firstName = request.firstName,
            lastName = request.lastName,
            email = request.email,
            emojis = emojis,
            password = request.password,
            status = status,
            roleIds = roleIds
        )

        val location = URI.create("$ENDPOINT/${entity.id}")
        val dto = GetUserResponse(entity)
        return ResponseEntity.created(location).body(dto)
    }

    @GetMapping("/{variable}")
    override fun get(
        @AuthenticationPrincipal user: UserEntity?,

        @PathVariable variable: String,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetUserResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        val id = variable.toLongOrNull()
        val entity = if (id != null) {
            userService.getById(id)
        } else {
            userService.getByUsername(variable)
        } ?: throw HttpException.NotFound()

        if (!user.hasPermission(Permission.MANAGE_USERS))
            if (entity.id != user.id)
                throw HttpException.Forbidden()

        val dto = GetUserResponse(entity)
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

        if (!user.hasPermission(Permission.MANAGE_USERS))
            throw HttpException.Forbidden()

        val sort = sortProperty?.let {
            Sort.by(sortDirection, sortProperty)
        } ?: Sort.unsorted()

        if (pageNumber != null) {
            val pageable = PageRequest.of(pageNumber, pageSize, sort)
            val page = userService.getPage(rsql = rsql, pageable = pageable)
            val dto = page.map { GetUserResponse(it) }
            return ResponseEntity.ok(dto)
        }

        val collection = userService.getAll(rsql = rsql, sort = sort).toMutableSet()
        val dto = collection.map { GetUserResponse(it) }
        return ResponseEntity.ok(dto)
    }

    @PatchMapping("/{id}")
    override fun patch(
        @AuthenticationPrincipal user: UserEntity?,

        @PathVariable id: Long,
        @RequestBody request: PatchUserRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetUserResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (user.id != id && !user.hasPermission(Permission.MANAGE_USERS))
            throw HttpException.Forbidden()

        var username: String? = null
        var roleIds: Set<Long>? = null
        var status: Status? = null
        var emojis: String? = null
        var email: String? = null
        if (user.hasPermission(Permission.MANAGE_USERS)) {
            request.username?.let { username = it }
            request.roleIds?.let { roleIds = it }
            request.status?.let { status = it }
            request.emojis?.let { emojis = it }
            request.email?.let { email = it }
        }

        val entity = userService.patch(
            id = id,
            username = username,
            firstName = request.firstName,
            lastName = request.lastName,
            email = email,
            emojis = emojis,
            password = request.password,
            status = status,
            roleIds = roleIds
        )

        val dto = GetUserResponse(entity)
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

        if (!user.hasPermission(Permission.MANAGE_USERS))
            if (id != user.id)
                throw HttpException.Forbidden()

        userService.delete(id)
        return ResponseEntity.noContent().build()
    }

}