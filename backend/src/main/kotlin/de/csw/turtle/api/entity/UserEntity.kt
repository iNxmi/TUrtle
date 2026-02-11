package de.csw.turtle.api.entity

import de.csw.turtle.api.Permission
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "users")
class UserEntity(

    @Id @GeneratedValue
    override val id: Long = 0,

    @Column(unique = true)
    var username: String,

    var firstName: String,

    var lastName: String,

    @Column(unique = true)
    var email: String,

    var password: String,

    @Column(unique = true)
    var emojis: String,

    var status: Status,

    @OneToOne
    val verificationToken: TokenEntity? = null,

    @OneToOne
    val resetPasswordToken: TokenEntity? = null,

    @ManyToMany
    @JoinTable(
        name = "user_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    val roles: MutableSet<RoleEntity> = mutableSetOf(),

    @OneToMany(mappedBy = "user")
    val auditLogs: Collection<AuditLogEntity> = emptySet(),

    @OneToMany(mappedBy = "user")
    val roomBookings: MutableSet<RoomBookingEntity> = mutableSetOf(),

    @ManyToMany(mappedBy = "whitelistedUsers")
    val whitelistedRoomBookings: MutableSet<RoomBookingEntity> = mutableSetOf(),

    @OneToMany(mappedBy = "user")
    val itemBookings: MutableSet<ItemBookingEntity> = mutableSetOf(),

    //Instant.MIN will be replaced by createdAt in prePersist()
    override var updatedAt: Instant = Instant.MIN,

    @Column(updatable = false)
    override val createdAt: Instant = Instant.now()

) : CRUDEntity {

    enum class Status {
        PENDING_VERIFICATION,
        PENDING_CONFIRMATION,
        ACTIVE,
        SUSPENDED,
        ARCHIVED,
        DELETED,
        REJECTED
    }

    fun getAllPermissions(): Set<Permission> = roles.flatMap { it.permissions }.toSet()
    fun hasPermission(permission: Permission): Boolean = getAllPermissions().contains(permission)

    @PrePersist
    fun prePersist() {
        updatedAt = createdAt
    }

    @PreUpdate
    fun preUpdate() {
        updatedAt = Instant.now()
    }

    override fun snapshot() = UserEntity(
        id = id,
        username = username,
        firstName = firstName,
        lastName = lastName,
        email = email,
        password = password,
        emojis = emojis,
        status = status,
        verificationToken = verificationToken,
        roles = roles.toMutableSet(),
        auditLogs = auditLogs.toMutableSet(),
        roomBookings = roomBookings.toMutableSet(),
        whitelistedRoomBookings = whitelistedRoomBookings.toMutableSet(),
        itemBookings = itemBookings.toMutableSet(),
        updatedAt = updatedAt,
        createdAt = createdAt
    )

}