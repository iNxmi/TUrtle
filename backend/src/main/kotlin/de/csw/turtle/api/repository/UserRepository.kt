package de.csw.turtle.api.repository

import de.csw.turtle.api.entity.UserEntity

interface UserRepository : CRUDRepository<UserEntity> {

    fun findByUsername(username: String): UserEntity?
    fun findByEmail(email: String): UserEntity?
    fun findByEmojis(emojis: String): UserEntity?

}
