package de.csw.turtle.api.service

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.time.Duration
import java.util.*
import javax.crypto.SecretKey

@Service
class JWTService(
    private val systemSettingService: SystemSettingService
) {

    private fun getKey(): SecretKey {
        val secret = systemSettingService.getTyped<String>("auth.jwt.secret")
        val bytes = Decoders.BASE64.decode(secret)
        return Keys.hmacShaKeyFor(bytes)
    }

    private fun getDurationRefresh() = systemSettingService.getTyped<Duration>("auth.jwt.duration.refresh")
    fun generateRefreshToken(userDetails: UserDetails) = generate(userDetails, getDurationRefresh())

    private fun getDurationAccess() = systemSettingService.getTyped<Duration>("auth.jwt.duration.access")
    fun generateAccessToken(userDetails: UserDetails) = generate(userDetails, getDurationAccess())

    private fun generate(userDetails: UserDetails, duration: Duration): String = Jwts.builder()
        .setSubject(userDetails.username)
        .setIssuedAt(Date(System.currentTimeMillis()))
        .setExpiration(Date(System.currentTimeMillis() + duration.toMillis()))
        .signWith(getKey())
        .compact()

    fun extract(token: String): String =
        Jwts.parser().setSigningKey(getKey()).parseClaimsJws(token).body.subject

    fun isValid(token: String, userDetails: UserDetails): Boolean =
        extract(token) == userDetails.username && !isExpired(token)

    fun isExpired(token: String): Boolean =
        Jwts.parser().setSigningKey(getKey()).parseClaimsJws(token).body.expiration.before(Date())

}