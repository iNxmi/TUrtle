package de.csw.turtle.api.entity

import de.csw.turtle.api.security.Role
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.Instant

@Entity
@Table(name = "users")
data class UserEntity(
    @Id
    @Column(name = "username", nullable = false, unique = true, updatable = false)
    var userName: String,

    @Column(nullable = false)
    var firstName: String,

    @Column(nullable = false)
    var lastName: String,

    @Column(nullable = false, unique = true)
    var email: String,

    @Column(nullable = false, unique = true)
    var studentId: Long,

    @Column(name = "password", nullable = false)
    var passwordHash: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var role: Role = Role.STUDENT,

    @Column(nullable = false, updatable = false)
    var createdAt: Instant = Instant.now()
) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        val authority = role.getGrantedAuthority()
        return setOf(authority)
    }

    override fun getUsername() = userName

    override fun getPassword() = passwordHash

}