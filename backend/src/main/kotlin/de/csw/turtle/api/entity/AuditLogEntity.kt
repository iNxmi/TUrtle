package de.csw.turtle.api.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "audit_logs")
data class AuditLogEntity(

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: UserEntity,

    @Column(nullable = false)
    var ipAddress: String,

    @Column(nullable = false)
    var endpoint: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var httpMethod: HttpMethod,

    @Id
    @GeneratedValue
    override val id: Long = 0,

    @Column(updatable = false)
    override val createdAt: Instant = Instant.now()

) : CRUDEntity() {

    enum class HttpMethod {
        GET, POST, PUT, DELETE, HEAD, OPTIONS, TRACE, PATCH
    }

}