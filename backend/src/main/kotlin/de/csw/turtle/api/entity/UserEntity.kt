package de.csw.turtle.api.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "users")
data class UserEntity(

    @Column(unique = true)
    var username: String,

    var firstName: String,

    var lastName: String,

    @Column(unique = true)
    var email: String,

    @Column(unique = true)
    var studentId: Long,

    var password: String,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    val roles: MutableSet<RoleEntity> = mutableSetOf(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val auditLogs: Collection<AuditLogEntity> = emptySet(),

    @OneToMany(mappedBy = "creator", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val roomBookings: Collection<RoomBookingEntity> = emptySet(),

    @ManyToMany(mappedBy = "whitelist")
    val whitelistedRoomBookings: MutableSet<RoomBookingEntity> = mutableSetOf()

) : CRUDEntity()