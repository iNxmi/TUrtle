package de.csw.turtle.api.entity

import jakarta.persistence.*
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

        DATE, TIME, INSTANT,

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
        USER_ENTITY_REFERENCE,
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

        class AuditLogEntityReferenceValue(val value: AuditLogEntity) : SettingValue()
        class DeviceBookingEntityReferenceValue(val value: DeviceBookingEntity) : SettingValue()
        class DeviceCategoryEntityReferenceValue(val value: DeviceCategoryEntity) : SettingValue()
        class DeviceEntityReferenceValue(val value: DeviceEntity) : SettingValue()
        class ExceptionEntityReferenceValue(val value: ExceptionEntity) : SettingValue()
        class FAQEntityReferenceValue(val value: FAQEntity) : SettingValue()
        class LockerEntityReferenceValue(val value: LockerEntity) : SettingValue()
        class RoleEntityReferenceValue(val value: RoleEntity) : SettingValue()
        class RoomBookingEntityReferenceValue(val value: RoomBookingEntity) : SettingValue()
        class SupportTicketEntityReferenceValue(val value: SupportTicketEntity) : SettingValue()
        class SystemSettingEntityReferenceValue(val value: SystemSettingEntity) : SettingValue()
        class TemplateEntityReferenceValue(val value: TemplateEntity) : SettingValue()
        class UserEntityReferenceValue(val value: UserEntity) : SettingValue()
    }

}