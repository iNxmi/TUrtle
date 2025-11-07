package de.csw.turtle.api.v1.repository

import de.csw.turtle.api.v1.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, String> {

    fun findByUserName(username: String): UserEntity?
    fun findByEmail(email: String): UserEntity?
    fun findByStudentId(studentId: Long): UserEntity?

    fun deleteByUserName(username: String)

}
