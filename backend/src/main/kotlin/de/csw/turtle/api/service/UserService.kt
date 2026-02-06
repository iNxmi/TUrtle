package de.csw.turtle.api.service

import de.csw.turtle.api.Settings
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.repository.EmailTemplateRepository
import de.csw.turtle.api.repository.RoleRepository
import de.csw.turtle.api.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import java.time.Duration
import java.time.Instant

@Service
class UserService(
    override val repository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val systemSettingService: SystemSettingService,
    private val thymeleafService: ThymeleafService,
    private val emailService: EmailService,
    private val roleRepository: RoleRepository,
    private val emailTemplateRepository: EmailTemplateRepository
) : CRUDService<UserEntity>() {

    fun generateEmojis(): String {
        val emojis = systemSettingService.getTyped<List<String>>(Settings.EMOJIS_ALL)
        val maxRetries = systemSettingService.getTyped<Int>(Settings.EMOJIS_MAX_RETRIES)
        val size = systemSettingService.getTyped<Int>(Settings.EMOJIS_SIZE)

        repeat(maxRetries) {
            val selected = emojis.shuffled().take(size).joinToString("")
            if (getByEmojisOrNull(selected) == null)
                return selected
        }

        throw HttpException.InternalServerError("Could not generate emojis. Contact system administrator. (Most likely too many users in DB).")
    }

    fun create(
        username: String,
        firstName: String,
        lastName: String,
        email: String,
        emojis: String,
        password: String,
        verified: Boolean,
        roleIds: Set<Long>
    ): UserEntity {
        val entity = UserEntity(
            username = username,
            firstName = firstName,
            lastName = lastName,
            email = email,
            emojis = emojis,
            password = passwordEncoder.encode(password),
            verified = verified,
            roles = roleIds.map { roleRepository.findById(it).get() }.toMutableSet()
        )

        if (!entity.verified) {
            val context = Context().apply {
                val fqdn = systemSettingService.getTyped<String>(Settings.GENERAL_FQDN)
                val duration = systemSettingService.getTyped<Duration>(Settings.USER_VERIFICATION_DURATION)

                setVariable("url", "https://$fqdn/api/auth/verify?token=${entity.verificationToken}")
                setVariable("user", entity)
                setVariable("duration", duration)
                setVariable("expiration", entity.createdAt.plusMillis(duration.toMillis()))
            }

            val templateId = systemSettingService.getTyped<Long>(Settings.EMAIL_TEMPLATE_USERS_VERIFY)
            val template = emailTemplateRepository.findById(templateId).get()

            val subject = thymeleafService.getRendered(template.subject, context)
            val content = thymeleafService.getRendered(template.content, context)
            emailService.sendHtmlEmail(entity.email, subject, content)
        }

        return repository.save(entity)
    }

    fun getUnverifiedUsers(cutoff: Instant): Set<UserEntity> = repository.findByVerifiedFalseAndCreatedAtBefore(cutoff)

    fun getByVerificationTokenOrNull(token: String): UserEntity? = repository.findByVerificationToken(token)
    fun getByVerificationToken(token: String): UserEntity =
        getByVerificationTokenOrNull(token) ?: throw HttpException.NotFound(token)

    fun getByUsernameOrNull(username: String): UserEntity? = repository.findByUsername(username)
    fun getByUsername(username: String): UserEntity =
        getByUsernameOrNull(username) ?: throw HttpException.NotFound(username)

    fun getByEmailOrNull(email: String): UserEntity? = repository.findByEmail(email)
    fun getByEmail(email: String): UserEntity =
        getByEmailOrNull(email) ?: throw HttpException.NotFound(email)

    fun getByEmojisOrNull(emojis: String): UserEntity? = repository.findByEmojis(emojis)
    fun getByEmojis(emojis: String): UserEntity = getByEmojisOrNull(emojis) ?: throw HttpException.NotFound(emojis)

    fun getByEmailOrUsernameOrNull(value: String): UserEntity? = repository.findByEmailOrUsername(value, value)
    fun getByEmailOrUsername(value: String): UserEntity =
        getByEmailOrUsernameOrNull(value) ?: throw HttpException.NotFound(value)

    @Transactional
    fun patch(
        id: Long,
        username: String? = null,
        firstName: String? = null,
        lastName: String? = null,
        email: String? = null,
        emojis: String? = null,
        password: String? = null,
        verified: Boolean? = null,
        roleIds: Set<Long>? = null
    ): UserEntity {
        val entity = repository.findById(id).get()

        username?.let { entity.username = it }
        firstName?.let { entity.firstName = it }
        lastName?.let { entity.lastName = it }
        email?.let { entity.email = it }
        emojis?.let { entity.emojis = it }
        password?.let { entity.password = passwordEncoder.encode(it) }
        verified?.let { entity.verified = it }
        roleIds?.let { ids ->
            val roles = ids.map { roleRepository.findById(it).get() }

            entity.roles.clear()
            entity.roles.addAll(roles)
        }

        return repository.save(entity)
    }

}