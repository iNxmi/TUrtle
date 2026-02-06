package de.csw.turtle.api.service

import de.csw.turtle.api.Settings
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class UserService(
    override val repository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val systemSettingService: SystemSettingService,
    private val thymeleafService: ThymeleafService,
    private val emailService: EmailService,
    private val emailTemplateService: EmailTemplateService
) : CRUDService<UserEntity>("User") {

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

//    override fun create(request: CreateUserRequest): UserEntity {
//        if (getByUsernameOrNull(request.username) != null)
//            throw HttpException.Conflict("Username '${request.username}' already exists")
//
//        else if (request.username.isBlank())
//            throw HttpException.BadRequest("Username cannot be blank.")
//
//        val hashed = request.copy(password = passwordEncoder.encode(request.password))
//        val entity = super.create(hashed)
//
//        if (!entity.verified) {
//            val context = Context().apply {
//                val fqdn = systemSettingService.getTyped<String>(Settings.GENERAL_FQDN)
//                val duration = systemSettingService.getTyped<Duration>(Settings.USER_VERIFICATION_DURATION)
//
//                setVariable("url", "https://$fqdn/api/auth/verify?token=${entity.verificationToken}")
//                setVariable("user", entity)
//                setVariable("duration", duration)
//                setVariable("expiration", entity.createdAt.plusMillis(duration.toMillis()))
//            }
//
//            val templateId = systemSettingService.getTyped<Long>(Settings.EMAIL_TEMPLATE_VERIFY)
//            val template = emailTemplateService.getById(templateId)
//
//            val subject = thymeleafService.getRendered(template.subject, context)
//            val content = thymeleafService.getRendered(template.content, context)
//            emailService.sendHtmlEmail(entity.email, subject, content)
//        }
//
//        return entity
//    }

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

//    override fun patch(id: Long, request: PatchUserRequest): UserEntity {
//        val patched = if (request.password != null) {
//            request.copy(password = passwordEncoder.encode(request.password))
//        } else request
//        return super.patch(id, patched)
//    }

}