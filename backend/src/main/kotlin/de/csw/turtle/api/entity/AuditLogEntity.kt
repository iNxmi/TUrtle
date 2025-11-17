package de.csw.turtle.api.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "audit_logs")
data class AuditLogEntity(

    @ManyToOne
    @JoinColumn(name = "username")
    val user: UserEntity,

    val ipAddress: String,

    val endpoint: String,

    @Enumerated(EnumType.STRING)
    val httpMethod: HttpMethod,

    @Id
    @GeneratedValue
    val id: Long = 0,

    @Column(updatable = false)
    val createdAt: Instant = Instant.now()

) {

    enum class HttpMethod {
        GET, POST, PUT, DELETE, HEAD, OPTIONS, TRACE, PATCH
    }

}