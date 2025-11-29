package de.csw.turtle.api.entity

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.time.Instant

@Entity
@Table(name = "roles")
class RoleEntity(
    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    val permissions: MutableSet<String> = mutableSetOf(),

    @ManyToMany(mappedBy = "roles")
    val users: MutableSet<UserEntity> = mutableSetOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    override val id: Long = 0,

    @Column(nullable = false, updatable = false)
    override val createdAt: Instant = Instant.now()
) : CRUDEntity() {

    fun grantedAuthorities(): Set<SimpleGrantedAuthority> = permissions.map { SimpleGrantedAuthority(it) }.toSet()

}