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
        DATE, TIME, INSTANT, DURATION
    }

}