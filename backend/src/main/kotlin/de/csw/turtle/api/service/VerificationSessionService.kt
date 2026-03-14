package de.csw.turtle.api.service

import de.csw.turtle.api.entity.ConfigurationEntity.Key
import de.csw.turtle.api.entity.EmailTemplateEntity
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.entity.VerificationSessionEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.repository.VerificationSessionRepository
import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import java.security.SecureRandom
import java.time.Duration
import java.time.Instant
import java.util.*

@Service
class VerificationSessionService(
    override val repository: VerificationSessionRepository,
    private val passwordEncoder: PasswordEncoder,
    private val emailTemplateService: EmailTemplateService,
    private val configurationService: ConfigurationService,
    private val emailService: EmailService
) : CRUDService<VerificationSessionEntity>() {

    private val random = SecureRandom()
    private fun generateCode(): String {
        val number = random.nextInt(1_000_000)
        return ("%06d").format(number)
    }

    fun getByUuid(uuid: UUID) = repository.findByUuid(uuid)

    fun get(user: UserEntity, type: VerificationSessionEntity.Type) =
        user.verificationSessions.firstOrNull { it.type == type }

    @Transactional
    fun create(
        user: UserEntity,
        type: VerificationSessionEntity.Type,
        duration: Duration
    ): VerificationSessionEntity {
        val code = generateCode()
        val entity = VerificationSessionEntity(
            user = user,
            type = type,
            codeHash = passwordEncoder.encode(code)!!,
            duration = duration
        )

        sendEmail(entity, code)

        return repository.save(entity)
    }

    @Transactional
    fun patch(
        id: Long,
        attempts: Int? = null,
        code: String? = null
    ): VerificationSessionEntity {
        val entity = repository.findById(id).get()

        attempts?.let { entity.attempts = it }
        code?.let { entity.codeHash = passwordEncoder.encode(code)!! }

        return repository.save(entity)
    }

    @Transactional
    fun resend(uuid: UUID): VerificationSessionEntity {
        val entity = repository.findByUuid(uuid)
            ?: throw HttpException.NotFound()

        //TODO make duration a system configuration
        if (!entity.canResend(Duration.ofSeconds(60)))
            throw HttpException.TooManyRequests()

        val code = generateCode()
        entity.codeHash = passwordEncoder.encode(code)!!

        entity.attempts = 0
        entity.lastSentAt = Instant.now()

        repository.save(entity)

        sendEmail(entity, code)

        return entity
    }

    @Transactional
    fun sendEmail(entity: VerificationSessionEntity, code: String) {
        val type = when (entity.type) {
            VerificationSessionEntity.Type.VERIFICATION -> EmailTemplateEntity.Type.USER_VERIFICATION
            VerificationSessionEntity.Type.PASSWORD_RESET -> EmailTemplateEntity.Type.USER_PASSWORD_RESET
        }

        val template = emailTemplateService.getByType(type)
            ?: throw NoSuchElementException()

        val user = entity.user
        val context = Context().apply {
            val duration = configurationService.getTyped<Duration>(Key.USER_VERIFICATION_DURATION)

            setVariable("user", user)
            setVariable("code", code)
            setVariable("duration", duration)
            setVariable("expiration", user.createdAt.plusMillis(duration.toMillis()))
        }

        emailService.send(user.email, template, context)
    }

}