package de.csw.turtle.api.entity

import de.csw.turtle.api.Permission
import de.csw.turtle.api.entity.UserEntity
import jakarta.persistence.*
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.time.Instant

@Entity
@Table(name = "roles")
class RoleEntity(
    @Column(nullable = false)
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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    override val id: Long = 0,

    @Column(nullable = false, updatable = false)
    override val createdAt: Instant = Instant.now()
) : CRUDEntity() {

    fun authorities(): Set<SimpleGrantedAuthority> = permissions.map { SimpleGrantedAuthority(it.permission) }.toSet()

}