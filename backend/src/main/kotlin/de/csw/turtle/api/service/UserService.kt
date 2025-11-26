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
import org.springframework.security.core.session.SessionRegistry
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    repository: UserRepository,
    mapper: UserMapper,
    private val passwordEncoderService: PasswordEncoderService,
    private val sessionRegistry: SessionRegistry
) : CRUDService<UserEntity, CreateUserRequest, GetUserResponse, PatchUserRequest, UserRepository, UserMapper>(
    repository,
    mapper
) {

    override fun create(request: CreateUserRequest): UserEntity {
        if (repository.findByUserName(request.username) != null)
            throw UsernameAlreadyExistsException(request.username)

        val hashed = request.copy(password = passwordEncoderService.encode(request.password))
        return super.create(hashed)
    }

    @Transactional
    fun updateProfile(username: String, request: PatchProfileRequest): UserEntity {
        val user = repository.findByUserName(username)
            ?: throw UserNotFoundException(username)

        request.firstName?.let { user.firstName = it }
        request.lastName?.let { user.lastName = it }
        request.email?.let { user.email = it }
        request.studentId?.let { user.studentId = it }
        request.password?.let { user.passwordHash = passwordEncoderService.encode(it) }

        return repository.save(user)
    }

    override fun patch(id: Long, request: PatchUserRequest): UserEntity {
        val patched = if (request.password != null) {
            request.copy(password = passwordEncoderService.encode(request.password))
        } else request
        return super.patch(id, patched)
    }

    override fun delete(id: Long) {
        super.delete(id)

        //TODO make session invalidation work when deleting a user
        sessionRegistry.getAllSessions(id, false).forEach { it.expireNow() }
    }


}