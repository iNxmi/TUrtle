package de.csw.turtle.api.service

import de.csw.turtle.api.Settings
import de.csw.turtle.api.entity.TokenEntity
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.event.CreatedUserEvent
import de.csw.turtle.api.event.PatchedUserEvent
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.repository.RoleRepository
import de.csw.turtle.api.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.context.ApplicationEventPublisher
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class UserService(
    override val repository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val systemSettingService: SystemSettingService,
    private val roleRepository: RoleRepository,
    private val eventPublisher: ApplicationEventPublisher
) : CRUDService<UserEntity>() {

    @Transactional
    fun generateEmojis(): String {
        val emojis = systemSettingService.getTyped<List<String>>(Settings.EMOJIS_ALL)
        val maxRetries = systemSettingService.getTyped<Int>(Settings.EMOJIS_MAX_RETRIES)
        val size = systemSettingService.getTyped<Int>(Settings.EMOJIS_SIZE)

        repeat(maxRetries) {
            val selected = emojis.shuffled().take(size).joinToString("")
            if (getByEmojis(selected) == null)
                return selected
        }

        throw HttpException.InternalServerError("Could not generate unique emojis. Contact system administrator. (Most likely too many users in DB).")
    }

    @Transactional
    fun create(
        username: String,
        firstName: String,
        lastName: String,
        email: String,
        emojis: String,
        password: String,
        status: UserEntity.Status,
        roleIds: Set<Long>
    ): UserEntity {
        if (username.isBlank())
            throw HttpException.BadRequest("Username cannot be blank.")

        //TODO firstName != empty
        //TODO lastName != empty
        //TODO email != empty

        if (repository.existsByUsername(username))
            throw HttpException.Conflict("Username '${username}' already exists.")

        //TODO email is valid (regex)
        //TODO email is unique
        //TODO emojis is unique
        //TODO password is min. 10 long, min. 1 number included, min. 1 symbol included, min. 1 upper and min. 1 lower case (maybe via regex)
        //TODO role ids really point to roles

        val entity = UserEntity(
            username = username,
            firstName = firstName,
            lastName = lastName,
            email = email,
            emojis = emojis,
            passwordHash = passwordEncoder.encode(password),
            status = status,
            roles = roleIds.map { roleRepository.findById(it).get() }.toMutableSet()
        )

        val saved = repository.save(entity)

        eventPublisher.publishEvent(CreatedUserEvent(saved))

        return saved
    }

    fun getByStatusEqualsAndCreatedAtBefore(status: UserEntity.Status, cutoff: Instant): Set<UserEntity> =
        repository.findByStatusEqualsAndCreatedAtBefore(status, cutoff)

    fun getByToken(token: TokenEntity): UserEntity? = repository.findByTokensContains(token)

    fun getByUsernameOrNull(username: String): UserEntity? = repository.findByUsername(username)
    fun getByUsername(username: String): UserEntity = getByUsernameOrNull(username)
        ?: throw HttpException.NotFound(username)

    fun getByEmailOrNull(email: String): UserEntity? = repository.findByEmail(email)
    fun getByEmail(email: String): UserEntity = getByEmailOrNull(email)
        ?: throw HttpException.NotFound(email)

    fun getByEmojis(emojis: String): UserEntity? = repository.findByEmojis(emojis)
    fun getByEmojisLegacyFix(emojis: String): UserEntity? {
        val all = repository.findByEmojisStartsWith("{bcrypt}")
        val user = all.firstOrNull { passwordEncoder.matches(emojis, it.emojis) }
            ?: return null

        user.emojis = emojis
        repository.save(user)

        return user
    }

    fun getByEmailOrUsernameOrNull(value: String): UserEntity? = repository.findByEmailOrUsername(value, value)
    fun getByEmailOrUsername(value: String): UserEntity = getByEmailOrUsernameOrNull(value)
        ?: throw HttpException.NotFound(value)

    @Transactional
    fun addToken(user: UserEntity, token: TokenEntity): UserEntity {
        user.tokens.add(token)
        return repository.save(user)
    }

    @Transactional
    fun removeToken(user: UserEntity, token: TokenEntity): UserEntity {
        user.tokens.remove(token)
        return repository.save(user)
    }

    @Transactional
    fun patch(
        id: Long,
        username: String? = null,
        firstName: String? = null,
        lastName: String? = null,
        email: String? = null,
        emojis: String? = null,
        password: String? = null,
        status: UserEntity.Status? = null,
        roleIds: Set<Long>? = null
    ): UserEntity {
        val entity = repository.findById(id).get()
        val pre = entity.snapshot()

        username?.let { entity.username = it }
        firstName?.let { entity.firstName = it }
        lastName?.let { entity.lastName = it }
        email?.let { entity.email = it }
        emojis?.let { entity.emojis = it }
        password?.let { entity.passwordHash = passwordEncoder.encode(it) }
        status?.let { entity.status = it }
        roleIds?.let { ids ->
            val roles = ids.map { roleRepository.findById(it).get() }

            entity.roles.clear()
            entity.roles.addAll(roles)
        }

        val post = repository.save(entity)

        val event = PatchedUserEvent(pre = pre, post = post)
        eventPublisher.publishEvent(event)

        return post
    }

}