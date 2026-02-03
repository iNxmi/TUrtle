package de.csw.turtle.api.entity

import jakarta.persistence.*

@Entity
@Table(name = "auditlogs")
class AuditLogEntity(

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: UserEntity?,

    var ipAddress: String,

    var endpoint: String,

    var status: Int,

    @Enumerated(EnumType.STRING)
    var httpMethod: HttpMethod

) : CRUDEntity() {

    enum class HttpMethod {
        GET, POST, PUT, DELETE, HEAD, OPTIONS, TRACE, PATCH
    }

}