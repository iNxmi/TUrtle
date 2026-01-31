package de.csw.turtle.api.entity

import jakarta.persistence.*

@Entity
@Table(name = "system_settings")
class SystemSettingEntity(

    @Column(unique = true)
    var key: String,

    @Enumerated(EnumType.STRING)
    var type: Type,

    var value: String

) : CRUDEntity() {

    enum class Type {
        BOOLEAN,

        INT, INT_LIST,
        LONG, LONG_LIST,

        FLOAT, FLOAT_LIST,
        DOUBLE, DOUBLE_LIST,

        STRING, STRING_LIST,

        //ISO-8601
        DATE, TIME, INSTANT, DURATION,

        AUDIT_LOG_ENTITY_REFERENCE,
        DEVICE_BOOKING_ENTITY_REFERENCE,
        DEVICE_CATEGORY_ENTITY_REFERENCE,
        DEVICE_ENTITY_REFERENCE,
        EXCEPTION_ENTITY_REFERENCE,
        FAQ_ENTITY_REFERENCE,
        LOCKER_ENTITY_REFERENCE,
        ROLE_ENTITY_REFERENCE,
        ROOM_BOOKING_ENTITY_REFERENCE,
        SUPPORT_TICKET_ENTITY_REFERENCE,
        SYSTEM_SETTING_ENTITY_REFERENCE,
        TEMPLATE_ENTITY_REFERENCE,
        USER_ENTITY_REFERENCE
    }

}