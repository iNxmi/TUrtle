package de.csw.turtle.api.entity

import de.csw.turtle.api.Role
import jakarta.persistence.*
import org.springframework.security.core.userdetails.UserDetails
import java.time.Instant

@Entity
@Table(name = "users")
data class UserEntity(
    @Column(name = "username", nullable = false, unique = true)
    var userName: String,

    @Column(nullable = false)
    var firstName: String,

    @Column(nullable = false)
    var lastName: String,

    @Column(nullable = false, unique = true)
    var email: String,

    @Column(nullable = false, unique = true)
    var studentId: Long,

    @Column(nullable = false, name = "password")
    var passwordHash: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var role: Role = Role.STUDENT,

    @Column(nullable = false)
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val auditLogs: Collection<AuditLogEntity> = emptyList(),

    @Column(nullable = false, updatable = false)
    val createdAt: Instant = Instant.now(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long = 0
) : CRUDEntity(), UserDetails {

    override fun getAuthorities() = role.getGrantedAuthorities()
    override fun getUsername() = userName
    override fun getPassword() = passwordHash

}