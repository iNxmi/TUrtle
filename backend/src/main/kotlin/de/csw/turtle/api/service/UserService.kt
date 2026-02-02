package de.csw.turtle.api.service

import de.csw.turtle.api.dto.create.CreateUserRequest
import de.csw.turtle.api.dto.get.GetUserResponse
import de.csw.turtle.api.dto.patch.PatchUserRequest
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.mapper.UserMapper
import de.csw.turtle.api.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant

@Service
class UserService(
    override val repository: UserRepository,
    override val mapper: UserMapper,
    private val passwordEncoder: PasswordEncoder
) : CRUDService<UserEntity, CreateUserRequest, GetUserResponse, PatchUserRequest>("User") {

    override fun create(request: CreateUserRequest): UserEntity {
        if (getByUsernameOrNull(request.username) != null)
            throw HttpException.Conflict("Username: ${request.username} already exists")

        val hashed = request.copy(password = passwordEncoder.encode(request.password))
        return super.create(hashed)
    }

    fun getUnverifiedUsers(cutoff: Instant): Set<UserEntity> = repository.findByVerifiedFalseAndCreatedAtBefore(cutoff)

    fun getByVerificationTokenOrNull(token: String): UserEntity? = repository.findByVerificationToken(token)
    fun getByVerificationToken(token: String): UserEntity = getByVerificationTokenOrNull(token) ?: throw HttpException.NotFound(token)

    fun getByUsernameOrNull(username: String): UserEntity? = repository.findByUsername(username)
    fun getByUsername(username: String): UserEntity = getByUsernameOrNull(username) ?: throw HttpException.NotFound(username)

    fun getByEmojisOrNull(emojis: String): UserEntity? = repository.findByEmojis(emojis)
    fun getByEmojis(emojis: String): UserEntity = getByEmojisOrNull(emojis) ?: throw HttpException.NotFound(emojis)

    override fun patch(id: Long, request: PatchUserRequest): UserEntity {
        val patched = if (request.password != null) {
            request.copy(password = passwordEncoder.encode(request.password))
        } else request
        return super.patch(id, patched)
    }

}