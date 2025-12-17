package de.csw.turtle.api.entity

import jakarta.persistence.*

@Entity
@Table(name = "auditlogs")
class AuditLogEntity(

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: UserEntity,

    @Column(nullable = false)
    var ipAddress: String,

    @Column(nullable = false)
    var endpoint: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var httpMethod: HttpMethod

) : CRUDEntity() {

    enum class HttpMethod {
        GET, POST, PUT, DELETE, HEAD, OPTIONS, TRACE, PATCH
    }

}