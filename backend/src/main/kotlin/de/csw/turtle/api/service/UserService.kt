package de.csw.turtle.api.service

import de.csw.turtle.api.entity.ConfigurationEntity.Key
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
    private val configurationService: ConfigurationService,
    private val roleRepository: RoleRepository,
    private val eventPublisher: ApplicationEventPublisher
) : CRUDService<UserEntity>() {

    private val regex = ("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$").toRegex()

    @Transactional
    fun generateEmojis(): String {
        val emojis = configurationService.getTyped<List<String>>(Key.EMOJIS_ALL)
        val maxRetries = configurationService.getTyped<Int>(Key.EMOJIS_MAX_RETRIES)
        val size = configurationService.getTyped<Int>(Key.EMOJIS_SIZE)

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
            throw HttpException.BadRequest("Username cannot be left blank.")
        if (firstName.isBlank())
            throw HttpException.BadRequest("First name cannot be left blank.")
        if (lastName.isBlank())
            throw HttpException.BadRequest("Last name cannot be left blank.")
        if (email.isBlank())
            throw HttpException.BadRequest("Email cannot be left blank.")

        if (repository.existsByUsername(username))
            throw HttpException.Conflict("Username '${username}' already exists.")

        if (repository.findByEmail(email) != null)
            throw HttpException.Conflict("Email '$email' already exists.")

        if (!regex.matches(email))
            throw HttpException.BadRequest("'${email}' is not a valid Email Address.")

        if (repository.findByEmojis(emojis) != null)
            throw HttpException.Conflict("Emoji password '$emojis' already exists.")

        for (roleId in roleIds) {
            if (!roleRepository.existsById(roleId))
                throw HttpException.BadRequest("Role with id '$roleId' does not exist.")
        }
        //TODO password is min. 10 long, min. 1 number included, min. 1 symbol included, min. 1 upper and min. 1 lower case (maybe via regex)

        //TODO uncomment when in prod
//        val regex = configurationService.getTyped<String>(Key.USER_PASSWORD_REGEX).toRegex()
//        if(!regex.matches(password))
//            throw HttpException.BadRequest("Password needs to match '$regex' good luck :)")

        val entity = UserEntity(
            username = username,
            firstName = firstName,
            lastName = lastName,
            email = email,
            emojis = emojis,
            passwordHash = passwordEncoder.encode(password)!!,
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
        val all = repository.findByEmojisStartsWith("$")
        val user = all.firstOrNull { user ->
            val legacyEmojis = emojis.replace("❄️", "❄").replace("✂️", "✂").replace("☎️", "☎")
            passwordEncoder.matches(legacyEmojis, user.emojis)
        } ?: return null

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

        if (username != null) {
            if (username.isBlank())
                throw HttpException.BadRequest("Username cannot be left blank.")
            if (repository.existsByUsername(username))
                throw HttpException.Conflict("Username '${username}' already exists.")
        }
        if (firstName != null)
            if (firstName.isBlank())
                throw HttpException.BadRequest("First name cannot be left blank.")

        if (lastName != null)
            if (lastName.isBlank())
                throw HttpException.BadRequest("Last name cannot be left blank.")

        if (email != null) {
            if (email.isBlank())
                throw HttpException.BadRequest("Email cannot be left blank.")
            if (repository.findByEmail(email) != null)
                throw HttpException.Conflict("Email '$email' already exists.")
            if (!regex.matches(email))
                throw HttpException.BadRequest("'${email}' is not a valid Email Address.")
        }

        if (emojis != null)
            if (repository.findByEmojis(emojis) != null)
                throw HttpException.Conflict("Emoji password '$emojis' already exists.")

        if (roleIds != null) {
            for (roleId in roleIds) {
                if (!roleRepository.existsById(roleId))
                    throw HttpException.BadRequest("Role with id '$roleId' does not exist.")
            }
        }

        if (password != null) {
            val regex = configurationService.getTyped<String>(Key.USER_PASSWORD_REGEX).toRegex()
            if (!regex.matches(password))
                throw HttpException.BadRequest("Password needs to match '$regex' good luck :)")
        }

        val entity = repository.findById(id).get()
        val pre = entity.snapshot()

        username?.let { entity.username = it }
        firstName?.let { entity.firstName = it }
        lastName?.let { entity.lastName = it }
        email?.let { entity.email = it }
        emojis?.let { entity.emojis = it }
        password?.let { entity.passwordHash = passwordEncoder.encode(it)!! }
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