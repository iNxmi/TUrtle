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
import org.thymeleaf.context.Context
import java.time.Duration
import java.time.Instant

@Service
class UserService(
    override val repository: UserRepository,
    override val mapper: UserMapper,
    private val passwordEncoder: PasswordEncoder,
    private val systemSettingService: SystemSettingService,
    private val thymeleafService: ThymeleafService,
    private val emailService: EmailService,
    private val emailTemplateService: EmailTemplateService
) : CRUDService<UserEntity, CreateUserRequest, GetUserResponse, PatchUserRequest>("User") {

    override fun create(request: CreateUserRequest): UserEntity {
        if (getByUsernameOrNull(request.username) != null)
            throw HttpException.Conflict("Username '${request.username}' already exists")
        else if(request.username.isBlank())
            throw HttpException.BadRequest("Username cannot be blank.")


        val hashed = request.copy(password = passwordEncoder.encode(request.password))
        val entity = super.create(hashed)

        if (!entity.verified) {
            val context = Context().apply {
                val fqdn = systemSettingService.getTyped<String>("general.fqdn")
                val duration = systemSettingService.getTyped<Duration>("user.verification.duration")

                setVariable("url", "https://$fqdn/api/auth/verify?token=${entity.verificationToken}")
                setVariable("user", entity)
                setVariable("duration", duration)
                setVariable("expiration", entity.createdAt.plusMillis(duration.toMillis()))
            }

            val templateId = systemSettingService.getTyped<Long>("email.template.verify")
            val template = emailTemplateService.get(templateId)

            val subject = thymeleafService.getRendered(template.subject, context)
            val content = thymeleafService.getRendered(template.content, context)
            emailService.sendHtmlEmail(entity.email, subject, content)
        }

        return entity
    }

    fun getUnverifiedUsers(cutoff: Instant): Set<UserEntity> = repository.findByVerifiedFalseAndCreatedAtBefore(cutoff)

    fun getByVerificationTokenOrNull(token: String): UserEntity? = repository.findByVerificationToken(token)
    fun getByVerificationToken(token: String): UserEntity =
        getByVerificationTokenOrNull(token) ?: throw HttpException.NotFound(token)

    fun getByUsernameOrNull(username: String): UserEntity? = repository.findByUsername(username)
    fun getByUsername(username: String): UserEntity =
        getByUsernameOrNull(username) ?: throw HttpException.NotFound(username)

    fun getByEmojisOrNull(emojis: String): UserEntity? = repository.findByEmojis(emojis)
    fun getByEmojis(emojis: String): UserEntity = getByEmojisOrNull(emojis) ?: throw HttpException.NotFound(emojis)

    override fun patch(id: Long, request: PatchUserRequest): UserEntity {
        val patched = if (request.password != null) {
            request.copy(password = passwordEncoder.encode(request.password))
        } else request
        return super.patch(id, patched)
    }

}