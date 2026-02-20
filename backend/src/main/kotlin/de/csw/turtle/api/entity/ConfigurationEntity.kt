package de.csw.turtle.api.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "configuration")
class ConfigurationEntity(

    @Id @GeneratedValue
    override val id: Long = 0,

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    var key: Key,

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

    enum class Key {
        GENERAL_FQDN,

        EMOJIS_ALL,
        EMOJIS_SIZE,
        EMOJIS_MAX_RETRIES,

        USER_VERIFICATION_DURATION,
        USER_EMAIL_TRUSTED,
        USER_PASSWORD_REGEX,

        ALTCHA_SECRET,
        ALTCHA_MAX_NUMBER,
        ALTCHA_DURATION,
        ALTCHA_TRUSTED_IPS,

        DOOR_OPEN_DURATION,
        DOOR_SCHEDULE_START,
        DOOR_SCHEDULE_END,
        DOOR_SSH_COMMAND,
        DOOR_SSH_HOSTNAME,
        DOOR_SSH_PORT,
        DOOR_SSH_USERNAME,
        DOOR_SSH_PASSWORD,

        LOCKER_SCHEDULE_START,
        LOCKER_SCHEDULE_END,
        LOCKER_SSH_COMMAND,
        LOCKER_SSH_HOSTNAME,
        LOCKER_SSH_PORT,
        LOCKER_SSH_USERNAME,
        LOCKER_SSH_PASSWORD,

        JWT_SECRET,
        JWT_DURATION_ACCESS,
        JWT_DURATION_REFRESH,

        SUPPORT_TICKET_SUBJECT_LENGTH,
        SUPPORT_TICKET_DESCRIPTION_LENGTH,

        ROOM_BOOKING_TITLE_LENGTH,
        ROOM_BOOKING_DESCRIPTION_LENGTH,

        ITEM_NAME_LENGTH,
        ITEM_DESCRIPTION_LENGTH,

        ITEM_CATEGORY_NAME_LENGTH,

        FAQ_NAME_LENGTH,
        FAQ_TITLE_LENGTH,



        URL_X,
        URL_INSTAGRAM,
        URL_GITHUB,

        SECURITY_BCRYPT_STRENGTH
    }

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

    override fun snapshot() = ConfigurationEntity(
        id = id,
        key = key,
        type = type,
        value = value,
        visibility = visibility,
        updatedAt = updatedAt,
        createdAt = createdAt
    )

}