package de.csw.turtle.api.entity

import de.csw.turtle.api.Permission
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "users")
class UserEntity(

    @Column(unique = true)
    var username: String,

    var firstName: String,

    var lastName: String,

    @Column(unique = true)
    var email: String,

    var password: String,

    @Column(unique = true)
    var emojis: String,

    var verified: Boolean = false,

    @Column(unique = true)
    val verificationToken: String = UUID.randomUUID().toString(),

    @ManyToMany
    @JoinTable(
        name = "user_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    val roles: MutableSet<RoleEntity> = mutableSetOf(),

    @OneToMany(mappedBy = "user")
    val auditLogs: Collection<AuditLogEntity> = emptySet(),

    @ManyToMany(mappedBy = "whitelist")
    val whitelistedRoomBookings: MutableSet<RoomBookingEntity> = mutableSetOf(),

    @OneToMany(mappedBy = "user")
    val deviceBookings: MutableSet<DeviceBookingEntity> = mutableSetOf()

) : CRUDEntity() {

    fun getAllPermissions(): Set<Permission> = roles.flatMap { it.permissions }.toSet()
    fun hasPermission(permission: Permission): Boolean = getAllPermissions().contains(permission)

}