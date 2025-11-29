package de.csw.turtle.api.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "users")
data class UserEntity(
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
    var password: String,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    val roles: MutableSet<RoleEntity> = mutableSetOf(),

    @Column(nullable = false)
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val auditLogs: Collection<AuditLogEntity> = emptyList(),

    @Id
    @GeneratedValue
    override val id: Long = 0,

    @Column(nullable = false, updatable = false)
    override val createdAt: Instant = Instant.now()
) : CRUDEntity()