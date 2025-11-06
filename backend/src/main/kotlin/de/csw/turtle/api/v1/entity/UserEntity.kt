package de.csw.turtle.api.v1.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "users")
data class UserEntity(
    @Id
    @Column(nullable = false, unique = true)
    var username: String,

    @Column(nullable = false)
    var firstName: String,

    @Column(nullable = false)
    var lastName: String,

    @Column(nullable = false, unique = true)
    var email: String,

    @Column(nullable = false, unique = true)
    var studentId: Long,

    @Column(nullable = false)
    var passwordHash: String,

    @Column(nullable = false)
    var role: Role = Role.STUDENT,

    @Column(nullable = false, updatable = false)
    var createdAt: Instant = Instant.now()
) {

    enum class Role {
        STUDENT, ADMIN, PROFESSOR
    }

}