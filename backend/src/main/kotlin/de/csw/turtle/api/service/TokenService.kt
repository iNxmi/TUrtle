package de.csw.turtle.api.service

import de.csw.turtle.api.entity.TokenEntity
import de.csw.turtle.api.repository.TokenRepository
import org.springframework.stereotype.Service
import java.time.Duration
import java.util.*

@Service
class TokenService(
    override val repository: TokenRepository
) : CRUDService<TokenEntity>() {

    fun getByUuid(uuid: String): TokenEntity? = repository.findByUuid(uuid)

    fun create(
        type: TokenEntity.Type,
        duration: Duration
    ): TokenEntity {
        val uuid = UUID.randomUUID().toString()
        val entity = TokenEntity(
            uuid = uuid,
            duration = duration,
            type = type
        )
        return repository.save(entity)
    }

}