package de.csw.turtle.api.v1.repository

import de.csw.turtle.api.v1.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository : JpaRepository<UserEntity, Long> {

    fun findByUsername(username: String): UserEntity?
    fun findByEmail(email: String): UserEntity?
    fun findByStudentId(studentId: Long): UserEntity?

    fun deleteByUsername(username: String)

}
