package de.csw.turtle.api.service

import de.csw.turtle.api.dto.create.CreateUserRequest
import de.csw.turtle.api.dto.get.GetUserResponse
import de.csw.turtle.api.dto.patch.PatchProfileRequest
import de.csw.turtle.api.dto.patch.PatchUserRequest
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.exceptions.user.UserNotFoundException
import de.csw.turtle.api.exception.exceptions.user.UsernameAlreadyExistsException
import de.csw.turtle.api.mapper.UserMapper
import de.csw.turtle.api.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    override val repository: UserRepository,
    override val mapper: UserMapper,
    private val passwordEncoder: PasswordEncoder
) : CRUDService<UserEntity, CreateUserRequest, GetUserResponse, PatchUserRequest>() {

    override fun create(request: CreateUserRequest): UserEntity {
        if (getOrNull(request.username) != null)
            throw UsernameAlreadyExistsException(request.username)

        val hashed = request.copy(password = passwordEncoder.encode(request.password))
        return super.create(hashed)
    }

    fun getOrNull(username: String) = repository.findByUsername(username)
    fun get(username: String) = getOrNull(username) ?: throw UserNotFoundException(username)

    @Transactional
    fun updateProfile(username: String, request: PatchProfileRequest): UserEntity {
        val user = get(username)

        request.username?.let { user.username = it }
        request.firstName?.let { user.firstName = it }
        request.lastName?.let { user.lastName = it }
        request.email?.let { user.email = it }
        request.studentId?.let { user.studentId = it }
        request.password?.let { user.password = passwordEncoder.encode(it) }

        return repository.save(user)
    }

    override fun patch(id: Long, request: PatchUserRequest): UserEntity {
        val patched = if (request.password != null) {
            request.copy(password = passwordEncoder.encode(request.password))
        } else request
        return super.patch(id, patched)
    }

}