package de.csw.turtle.api.entity

import de.csw.turtle.api.Permission
import jakarta.persistence.*
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.time.Instant

@Entity
@Table(name = "roles")
class RoleEntity(
    @Column(nullable = false, unique = true)
    var name: String,

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "role_permissions",
        joinColumns = [JoinColumn(name = "role_id")],
    )
    @Enumerated(EnumType.STRING)
    val permissions: MutableSet<Permission> = mutableSetOf(),

    @ManyToMany(mappedBy = "roles")
    val users: MutableSet<UserEntity> = mutableSetOf(),

    @Id @GeneratedValue
    override val id: Long = 0,

    @Column(nullable = false, updatable = false)
    override val createdAt: Instant = Instant.now()
) : CRUDEntity() {

    fun authorities() = permissions.map { SimpleGrantedAuthority(it.name) }

}