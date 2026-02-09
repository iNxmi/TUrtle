package de.csw.turtle.api.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "audit_logs")
class AuditLogEntity(

    @Id @GeneratedValue
    override val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: UserEntity?,

    var ipAddress: String,

    var endpoint: String,

    var status: Int,

    @Enumerated(EnumType.STRING)
    var httpMethod: HttpMethod,

    //Instant.MIN will be replaced by createdAt in prePersist()
    override var updatedAt: Instant = Instant.MIN,

    @Column(updatable = false)
    override val createdAt: Instant = Instant.now()

) : CRUDEntity {

    enum class HttpMethod {
        GET, POST, PUT, DELETE, HEAD, OPTIONS, TRACE, PATCH
    }

    @PrePersist
    fun prePersist() {
        updatedAt = createdAt
    }

    @PreUpdate
    fun preUpdate() {
        updatedAt = Instant.now()
    }

    override fun snapshot() = AuditLogEntity(
        id = id,
        user = user,
        ipAddress = ipAddress,
        endpoint = endpoint,
        status = status,
        httpMethod = httpMethod,
        updatedAt = updatedAt,
        createdAt = createdAt
    )

}