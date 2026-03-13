package de.csw.turtle.api.service

import de.csw.turtle.api.entity.TokenEntity
import de.csw.turtle.api.repository.TokenRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.security.SecureRandom
import java.time.Duration
import java.util.*

@Service
class TokenService(
    override val repository: TokenRepository
) : CRUDService<TokenEntity>() {

    fun getByCode(code: String): TokenEntity? = repository.findByCode(code)

    @Transactional
    fun create(
        type: TokenEntity.Type,
        duration: Duration
    ): TokenEntity {
        val code = getCode()
        val entity = TokenEntity(
            code = code,
            duration = duration,
            type = type
        )
        return repository.save(entity)
    }

    private val random = SecureRandom()
    private fun getCode(): String {
        val number = random.nextInt(1_000_000)
        return ("%06d").format(number)
    }

}