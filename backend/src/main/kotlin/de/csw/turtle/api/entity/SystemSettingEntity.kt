package de.csw.turtle.api.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime

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
        DATE, TIME, INSTANT
    }

    sealed class SettingValue {
        class BooleanValue(val value: Boolean) : SettingValue()

        class IntValue(val value: Int) : SettingValue()
        class IntListValue(val value: List<Int>) : SettingValue()

        class LongValue(val value: Long) : SettingValue()
        class LongListValue(val value: List<Long>) : SettingValue()

        class FloatValue(val value: Float) : SettingValue()
        class FloatListValue(val value: List<Float>) : SettingValue()

        class DoubleValue(val value: Double) : SettingValue()
        class DoubleListValue(val value: List<Double>) : SettingValue()

        class StringValue(val value: String) : SettingValue()
        class StringListValue(val value: List<String>) : SettingValue()

        // Format -> YYYY-MM-DD
        class DateValue(val value: LocalDate) : SettingValue()
        // Format -> HH:MM:SS
        class TimeValue(val value: LocalTime) : SettingValue()
        // Format (ISO 8601) -> YYYY-MM-DDTHH:MM:SSZ
        class InstantValue(val value: Instant) : SettingValue()
    }

}