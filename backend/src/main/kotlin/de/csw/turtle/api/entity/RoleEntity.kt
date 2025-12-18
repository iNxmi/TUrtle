package de.csw.turtle.api.entity

import de.csw.turtle.api.Permission
import jakarta.persistence.*
import org.springframework.security.core.authority.SimpleGrantedAuthority

@Entity
@Table(name = "roles")
class RoleEntity(

    @Column(unique = true)
    var name: String,


    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(
        name = "role_permissions",
        joinColumns = [JoinColumn(name = "role_id")],
    )
    val permissions: MutableSet<Permission> = mutableSetOf(),

    @ManyToMany(mappedBy = "roles")
    val users: MutableSet<UserEntity> = mutableSetOf()

) : CRUDEntity() {

    fun authorities() = permissions.map { SimpleGrantedAuthority(it.name) }

}