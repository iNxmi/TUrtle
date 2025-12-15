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

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val auditLogs: Collection<AuditLogEntity> = emptySet(),

    @OneToMany(mappedBy = "creator", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val roomBookings: Collection<RoomBookingEntity> = emptySet(),

    @ManyToMany(mappedBy = "whitelist")
    val whitelistedRoomBookings: MutableSet<RoomBookingEntity> = mutableSetOf()

) : CRUDEntity() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UserEntity) return false

        if (username != other.username) return false

        return true
    }

    override fun hashCode(): Int {
        return username.hashCode()
    }

    override fun toString(): String {
        return "UserEntity(id=$id, username='$username', firstName='$firstName', lastName='$lastName', email='$email')"
    }
}