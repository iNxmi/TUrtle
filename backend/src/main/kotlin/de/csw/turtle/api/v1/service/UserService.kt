package de.csw.turtle.api.v1.service

import de.csw.turtle.api.v1.dto.request.CreateUserRequest
import de.csw.turtle.api.v1.dto.request.UpdateUserRequest
import de.csw.turtle.api.v1.entity.UserEntity
import de.csw.turtle.api.v1.exception.exceptions.UserNotFoundException
import de.csw.turtle.api.v1.exception.exceptions.UsernameAlreadyExistsException
import de.csw.turtle.api.v1.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.security.core.session.SessionRegistry
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val repository: UserRepository,
    private val passwordEncoderService: PasswordEncoderService,
    private val sessionRegistry: SessionRegistry
) {

    @Transactional
    fun create(request: CreateUserRequest): UserEntity {
        if (repository.findByUserName(request.username) != null)
            throw UsernameAlreadyExistsException(request.username)

        val user = request.create(passwordEncoderService.encoder)
        repository.save(user)

        return user
    }

    fun getAllPaged(page: Int, size: Int): Page<UserEntity> {
        val pageable = PageRequest.of(page, size)
        return repository.findAll(pageable)
    }

    fun getByUsername(username: String) = repository.findByUserName(username)
        ?: throw UserNotFoundException(username)

    @Transactional
    fun update(username: String, request: UpdateUserRequest): UserEntity {
        val user = repository.findByUserName(username)
            ?: throw UserNotFoundException(username)

        request.username?.let { user.userName = it }
        request.firstName?.let { user.firstName = it }
        request.lastName?.let { user.lastName = it }
        request.email?.let { user.email = it }
        request.studentId?.let { user.studentId = it }
        request.password?.let { user.passwordHash = passwordEncoderService.encode(it) }

        repository.save(user)
        return user
    }

    @Transactional
    fun delete(username: String) {
        val user = repository.findByUserName(username)
            ?: throw UserNotFoundException(username)

        //TODO make session invalidation work when deleting a user
        sessionRegistry.getAllSessions(user.username, false).forEach { it.expireNow() }

        repository.delete(user)
    }

}