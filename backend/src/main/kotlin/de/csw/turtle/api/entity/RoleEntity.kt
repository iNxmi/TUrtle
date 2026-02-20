package de.csw.turtle.api.entity

import de.csw.turtle.api.Permission
import jakarta.persistence.*
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.time.Instant

@Entity
@Table(name = "roles")
class RoleEntity(

    @Id @GeneratedValue
    override val id: Long = 0,

    @Column(unique = true)
    var name: String,

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(
        name = "role_permissions",
        joinColumns = [JoinColumn(name = "role_id")],
    )
    @Column(name = "permission")
    val permissions: MutableSet<Permission>,

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    var type: Type?,

    @ManyToMany(mappedBy = "roles")
    val users: MutableSet<UserEntity> = mutableSetOf(),

    //Instant.MIN will be replaced by createdAt in prePersist()
    override var updatedAt: Instant = Instant.MIN,

    @Column(updatable = false)
    override val createdAt: Instant = Instant.now()

) : CRUDEntity {

    enum class Type {
        STUDENT,
        PROFESSOR,
        ADMINISTRATOR
    }

    fun authorities() = permissions.map { SimpleGrantedAuthority(it.name) }

    @PrePersist
    fun prePersist() {
        updatedAt = createdAt
    }

    @PreUpdate
    fun preUpdate() {
        updatedAt = Instant.now()
    }

    override fun snapshot() = RoleEntity(
        id = id,
        name = name,
        permissions = permissions.toMutableSet(),
        type = type,
        users = users.toMutableSet(),
        updatedAt = updatedAt,
        createdAt = createdAt
    )

}