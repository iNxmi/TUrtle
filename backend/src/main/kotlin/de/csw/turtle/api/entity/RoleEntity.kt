//package de.csw.turtle.api.entity
//
//import de.csw.turtle.api.config.security.Permission
//import jakarta.persistence.CollectionTable
//import jakarta.persistence.Column
//import jakarta.persistence.ElementCollection
//import jakarta.persistence.Entity
//import jakarta.persistence.EnumType
//import jakarta.persistence.Enumerated
//import jakarta.persistence.Id
//import jakarta.persistence.JoinColumn
//
//@Entity
//class RoleEntity(
//    @Id
//    val name: String,
//
//    @ElementCollection(targetClass = Permission::class)
//    @CollectionTable(
//        name = "role_permissions",
//        joinColumns = [JoinColumn(name = "role_name")]
//    )
//    @Enumerated(EnumType.STRING)
//    @Column(name = "permissions", nullable = false)
//    val permissions: MutableSet<Permission> = mutableSetOf(Permission.ALL),
//
//    @Column(nullable = false)
//    val color: Int = 0xFFFFFF
//)