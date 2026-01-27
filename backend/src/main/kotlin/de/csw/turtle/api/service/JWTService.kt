package de.csw.turtle.api.service

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
class JWTService {

    //TODO change into config or system settings
    private val SECRET_KEY =
        "Zbd7rLTbLcEyhygmMCfvgqLAYggKgFyxKKMC54oVbbMiMgh+49ZW2TP3YswvegWsnQoVN4kznt94CMNVogPbbhH42EDFsrDdYz3"
    private val DURATION_MS: Long = 30L * 24L * 60L * 60L * 1000L // 30 Days

    fun generate(userDetails: UserDetails): String = Jwts.builder()
        .setSubject(userDetails.username)
        .setIssuedAt(Date(System.currentTimeMillis()))
        .setExpiration(Date(System.currentTimeMillis() + DURATION_MS))
        .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
        .compact()

    fun extract(token: String): String =
        Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).body.subject

    fun isValid(token: String, userDetails: UserDetails): Boolean =
        extract(token) == userDetails.username && !isExpired(token)

    fun isExpired(token: String): Boolean =
        Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).body.expiration.before(Date())

}