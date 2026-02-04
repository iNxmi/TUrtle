package de.csw.turtle.api.repository

import de.csw.turtle.api.entity.UserEntity
import java.time.Instant

interface UserRepository : CRUDRepository<UserEntity> {

    fun findByEmailOrUsername(email: String, username: String): UserEntity?
    fun findByEmail(username: String): UserEntity?
    fun findByUsername(username: String): UserEntity?
    fun findByVerificationToken(token: String): UserEntity?
    fun findByEmojis(emojis: String): UserEntity?
    fun findByVerifiedFalseAndCreatedAtBefore(cutoff: Instant): Set<UserEntity>

}
