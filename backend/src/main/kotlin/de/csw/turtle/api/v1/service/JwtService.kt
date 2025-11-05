package de.csw.turtle.api.v1.service

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JwtService {

    //TODO move secret key to .env
    private val secret = "super-secret-key-keep-safe-please-change"
    private val key = Keys.hmacShaKeyFor(secret.toByteArray())
    private val duration = 30 * 24 * 60 * 60 * 1000

    fun generate(username: String) = Jwts.builder()
        .setSubject(username)
        .setIssuedAt(Date())
        .setExpiration(Date(System.currentTimeMillis() + duration))
        .signWith(key)
        .compact()

    fun extract(token: String) = Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .body
        .subject

}