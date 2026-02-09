package de.csw.turtle.api.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "system_settings")
class SystemSettingEntity(

    @Id @GeneratedValue
    override val id: Long = 0,

    @Column(unique = true)
    var key: String,

    @Enumerated(EnumType.STRING)
    var type: Type,

    var value: String,

    @Enumerated(EnumType.STRING)
    var visibility: Visibility,

    //Instant.MIN will be replaced by createdAt in prePersist()
    override var updatedAt: Instant = Instant.MIN,

    @Column(updatable = false)
    override val createdAt: Instant = Instant.now()

) : CRUDEntity {

    enum class Type {
        BOOLEAN,

        INT, INT_LIST,
        LONG, LONG_LIST,

        FLOAT, FLOAT_LIST,
        DOUBLE, DOUBLE_LIST,

        STRING, STRING_LIST,

        //ISO-8601
        DATE, TIME, INSTANT, DURATION
    }

    enum class Visibility {
        PUBLIC, PRIVATE
    }

    @PrePersist
    fun prePersist() {
        updatedAt = createdAt
    }

    @PreUpdate
    fun preUpdate() {
        updatedAt = Instant.now()
    }

    override fun snapshot() = SystemSettingEntity(
        id = id,
        key = key,
        type = type,
        value = value,
        visibility = visibility,
        updatedAt = updatedAt,
        createdAt = createdAt
    )

}