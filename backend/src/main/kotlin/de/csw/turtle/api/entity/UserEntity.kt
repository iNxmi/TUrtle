package de.csw.turtle.api.entity

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class UserEntity(

    @Column(unique = true)
    var username: String,

    var firstName: String,

    var lastName: String,

    @Column(unique = true)
    var email: String,

    var password: String,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    val roles: MutableSet<RoleEntity> = mutableSetOf(),

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    val auditLogs: Collection<AuditLogEntity> = emptySet()

) : CRUDEntity()