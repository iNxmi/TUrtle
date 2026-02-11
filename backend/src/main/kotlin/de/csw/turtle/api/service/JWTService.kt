package de.csw.turtle.api.service

import de.csw.turtle.api.Settings
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant
import java.util.*
import javax.crypto.SecretKey

private const val CLAIM_TYPE_NAME = "type"

@Service
class JWTService(
    private val systemSettingService: SystemSettingService
) {

    private fun getKey(): SecretKey {
        val secret = systemSettingService.getTyped<String>(Settings.JWT_SECRET)
        val bytes = Decoders.BASE64.decode(secret)
        return Keys.hmacShaKeyFor(bytes)
    }

    fun generate(userId: Long, type: Type): String {
        val duration = systemSettingService.getTyped<Duration>(type.setting)

        val millis = System.currentTimeMillis()
        val issuedAt = Date(millis)
        val expiration = Date(millis + duration.toMillis())

        val token = Jwts.builder()
            .setSubject(userId.toString())
            .claim(CLAIM_TYPE_NAME, type.name)
            .setIssuedAt(issuedAt)
            .setExpiration(expiration)
            .signWith(getKey())
            .compact()

        return token
    }

    fun getData(token: String): Data {
        val claims = getClaims(token)

        val subject = claims.subject.toLong()

        val typeName = claims[CLAIM_TYPE_NAME, String::class.java]
        val type = Type.valueOf(typeName)

        val issuedAt = claims.issuedAt.toInstant()
        val expiration = claims.expiration.toInstant()

        return Data(subject, type, issuedAt, expiration)
    }

    fun isExpired(token: String): Boolean {
        val expiration = getData(token).expiration
        val now = Instant.now()
        return expiration.isBefore(now)
    }

    private fun getClaims(token: String): Claims = Jwts.parserBuilder()
        .setSigningKey(getKey())
        .build()
        .parseClaimsJws(token)
        .body

    enum class Type(val setting: Settings) {
        ACCESS(Settings.JWT_DURATION_ACCESS),
        REFRESH(Settings.JWT_DURATION_REFRESH);
    }

    data class Data(
        val subject: Long,
        val type: Type,
        val issuedAt: Instant,
        val expiration: Instant
    )

}