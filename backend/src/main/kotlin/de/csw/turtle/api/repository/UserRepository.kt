package de.csw.turtle.api.repository

import de.csw.turtle.api.entity.UserEntity

interface UserRepository : CRUDRepository<UserEntity> {

    fun findByUserName(username: String): UserEntity?
    fun findByEmail(email: String): UserEntity?
    fun findByStudentId(studentId: Long): UserEntity?

    fun deleteByUserName(username: String)

}
