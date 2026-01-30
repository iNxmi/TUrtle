package de.csw.turtle.api.service

import de.csw.turtle.api.dto.create.CreateUserRequest
import de.csw.turtle.api.dto.get.GetUserResponse
import de.csw.turtle.api.dto.patch.PatchUserRequest
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.ConflictException
import de.csw.turtle.api.exception.NotFoundException
import de.csw.turtle.api.mapper.UserMapper
import de.csw.turtle.api.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    override val repository: UserRepository,
    override val mapper: UserMapper,
    private val passwordEncoder: PasswordEncoder
) : CRUDService<UserEntity, CreateUserRequest, GetUserResponse, PatchUserRequest>("User") {

    override fun create(request: CreateUserRequest): UserEntity {
        if (getByUsernameOrNull(request.username) != null)
            throw ConflictException("Username: ${request.username} already exists")

        val hashed = request.copy(password = passwordEncoder.encode(request.password))
        return super.create(hashed)
    }

    fun getByUsernameOrNull(username: String): UserEntity? = repository.findByUsername(username)
    fun getByUsername(username: String): UserEntity = getByUsernameOrNull(username) ?: throw NotFoundException(username)

    fun getByEmojisOrNull(emojis: String): UserEntity? = repository.findByEmojis(emojis)
    fun getByEmojis(emojis: String): UserEntity = getByEmojisOrNull(emojis) ?: throw NotFoundException(emojis)

    override fun patch(id: Long, request: PatchUserRequest): UserEntity {
        val patched = if (request.password != null) {
            request.copy(password = passwordEncoder.encode(request.password))
        } else request
        return super.patch(id, patched)
    }

}