package de.csw.turtle.api.controller

import de.csw.turtle.api.dto.request.CreateUserRequest
import de.csw.turtle.api.dto.request.PatchUserRequest
import de.csw.turtle.api.dto.response.GetUserResponse
import de.csw.turtle.api.exception.exceptions.user.UserNotFoundException
import de.csw.turtle.api.security.Permission.*
import de.csw.turtle.api.security.RequiresPermission
import de.csw.turtle.api.service.UserService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort.Direction
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {

    @RequiresPermission(API_USERS_CREATE)
    @PostMapping
    fun create(
        @RequestBody createUserRequest: CreateUserRequest
    ): ResponseEntity<GetUserResponse> {
        val user = userService.create(createUserRequest)
        return ResponseEntity
            .created(URI.create("/api/users/${user.username}"))
            .body(GetUserResponse(user))
    }

    @RequiresPermission(API_USERS_GET_PAGINATED)
    @GetMapping
    fun getPaginated(
        @RequestParam(name = "page", required = false) pageNumber: Int = 0,
        @RequestParam(name = "size", required = false) pageSize: Int = 20,
        @RequestParam(name = "sort", required = false) sort: Array<String> = emptyArray(),
        @RequestParam(name = "direction", required = false) direction: Direction = Direction.DESC
    ): ResponseEntity<Page<GetUserResponse>> {
        val pageRequest = if (sort.isEmpty()) {
            PageRequest.of(pageNumber, pageSize)
        } else {
            PageRequest.of(pageNumber, pageSize, direction, *sort)
        }
        val page = userService.getAllPaged(pageRequest).map { GetUserResponse(it) }
        return ResponseEntity.ok(page)
    }

    @RequiresPermission(API_USERS_GET_ONE)
    @GetMapping("/{username}")
    fun getByUsername(
        @PathVariable username: String
    ): ResponseEntity<GetUserResponse> {
        val user = userService.getByUsername(username)
            ?: throw UserNotFoundException(username)

        return ResponseEntity.ok(GetUserResponse(user))
    }

    @RequiresPermission(API_USERS_PATCH)
    @PatchMapping("/{username}")
    fun patchByUsername(
        @PathVariable username: String,
        @RequestBody patchUserRequest: PatchUserRequest
    ): ResponseEntity<GetUserResponse> {
        val user = userService.update(username, patchUserRequest)
        return ResponseEntity.ok(GetUserResponse(user))
    }

    @RequiresPermission(API_USERS_DELETE)
    @DeleteMapping("/{username}")
    fun deleteByUsername(
        @PathVariable username: String
    ): ResponseEntity<Void> {
        userService.delete(username)
        return ResponseEntity.noContent().build()
    }

}