package de.csw.turtle.api.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import de.csw.turtle.api.dto.create.CreateSystemSettingRequest
import de.csw.turtle.api.dto.get.GetSystemSettingResponse
import de.csw.turtle.api.dto.patch.PatchSystemSettingRequest
import de.csw.turtle.api.entity.*
import de.csw.turtle.api.exception.BadRequestException
import de.csw.turtle.api.exception.NotFoundException
import de.csw.turtle.api.mapper.SystemSettingMapper
import de.csw.turtle.api.repository.SystemSettingRepository
import de.csw.turtle.api.service.locker.LockerService
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime

@Service
class SystemSettingService(
    override val repository: SystemSettingRepository,
    override val mapper: SystemSettingMapper,

    private val objectMapper: ObjectMapper,

    private val auditLogService: AuditLogService,
    private val deviceBookingService: DeviceBookingService,
    private val deviceCategoryService: DeviceCategoryService,
    private val deviceService: DeviceService,
    private val exceptionService: ExceptionService,
    private val faqService: FAQService,
    private val lockerService: LockerService,
    private val roleService: RoleService,
    private val roomBookingService: RoomBookingService,
    private val supportTicketService: SupportTicketService,
    private val templateService: TemplateService,
    private val userService: UserService
) : CRUDService<SystemSettingEntity, CreateSystemSettingRequest, GetSystemSettingResponse, PatchSystemSettingRequest>("SystemSetting") {

    fun getBoolean(key: String): Boolean {
        val entity = getByKey(key)
        return (parse(entity) as SystemSettingEntity.SettingValue.BooleanValue).value
    }

    fun getInt(key: String): Int {
        val entity = getByKey(key)
        return (parse(entity) as SystemSettingEntity.SettingValue.IntValue).value
    }

    fun getIntList(key: String): List<Int> {
        val entity = getByKey(key)
        return (parse(entity) as SystemSettingEntity.SettingValue.IntListValue).value
    }

    fun getFloat(key: String): Float {
        val entity = getByKey(key)
        return (parse(entity) as SystemSettingEntity.SettingValue.FloatValue).value
    }

    fun getFloatList(key: String): List<Float> {
        val entity = getByKey(key)
        return (parse(entity) as SystemSettingEntity.SettingValue.FloatListValue).value
    }

    fun getString(key: String): String {
        val entity = getByKey(key)
        return (parse(entity) as SystemSettingEntity.SettingValue.StringValue).value
    }

    fun getStringList(key: String): List<String> {
        val entity = getByKey(key)
        return (parse(entity) as SystemSettingEntity.SettingValue.StringListValue).value
    }

    fun getDate(key: String): LocalDate {
        val entity = getByKey(key)
        return (parse(entity) as SystemSettingEntity.SettingValue.DateValue).value
    }

    fun getTime(key: String): LocalTime {
        val entity = getByKey(key)
        return (parse(entity) as SystemSettingEntity.SettingValue.TimeValue).value
    }

    fun getInstant(key: String): Instant {
        val entity = getByKey(key)
        return (parse(entity) as SystemSettingEntity.SettingValue.InstantValue).value
    }

    fun getAuditLogEntity(key: String): AuditLogEntity {
        val entity = getByKey(key)
        return (parse(entity) as SystemSettingEntity.SettingValue.AuditLogEntityReferenceValue).value
    }

    fun getDeviceBookingEntity(key: String): DeviceBookingEntity {
        val entity = getByKey(key)
        return (parse(entity) as SystemSettingEntity.SettingValue.DeviceBookingEntityReferenceValue).value
    }

    fun getDeviceCategoryEntity(key: String): DeviceCategoryEntity {
        val entity = getByKey(key)
        return (parse(entity) as SystemSettingEntity.SettingValue.DeviceCategoryEntityReferenceValue).value
    }

    fun getDeviceEntity(key: String): DeviceEntity {
        val entity = getByKey(key)
        return (parse(entity) as SystemSettingEntity.SettingValue.DeviceEntityReferenceValue).value
    }

    fun getExceptionEntity(key: String): ExceptionEntity {
        val entity = getByKey(key)
        return (parse(entity) as SystemSettingEntity.SettingValue.ExceptionEntityReferenceValue).value
    }

    fun getFAQEntity(key: String): FAQEntity {
        val entity = getByKey(key)
        return (parse(entity) as SystemSettingEntity.SettingValue.FAQEntityReferenceValue).value
    }

    fun getLockerEntity(key: String): LockerEntity {
        val entity = getByKey(key)
        return (parse(entity) as SystemSettingEntity.SettingValue.LockerEntityReferenceValue).value
    }

    fun getRoleEntity(key: String): RoleEntity {
        val entity = getByKey(key)
        return (parse(entity) as SystemSettingEntity.SettingValue.RoleEntityReferenceValue).value
    }

    fun getRoomBookingEntity(key: String): RoomBookingEntity {
        val entity = getByKey(key)
        return (parse(entity) as SystemSettingEntity.SettingValue.RoomBookingEntityReferenceValue).value
    }

    fun getSupportTicketEntity(key: String): SupportTicketEntity {
        val entity = getByKey(key)
        return (parse(entity) as SystemSettingEntity.SettingValue.SupportTicketEntityReferenceValue).value
    }

    fun getSystemSettingEntity(key: String): SystemSettingEntity {
        val entity = getByKey(key)
        return (parse(entity) as SystemSettingEntity.SettingValue.SystemSettingEntityReferenceValue).value
    }

    fun getTemplateEntity(key: String): TemplateEntity {
        val entity = getByKey(key)
        return (parse(entity) as SystemSettingEntity.SettingValue.TemplateEntityReferenceValue).value
    }

    fun getUserEntity(key: String): UserEntity {
        val entity = getByKey(key)
        return (parse(entity) as SystemSettingEntity.SettingValue.UserEntityReferenceValue).value
    }

    fun getByKeyOrNull(key: String): SystemSettingEntity? = repository.findByKey(key)
    fun getByKey(key: String): SystemSettingEntity = repository.findByKey(key)
        ?: throw NotFoundException("System setting with key '$key' not found.")

    fun parse(entity: SystemSettingEntity) = parse(entity.key, entity.type, entity.value)
    fun parse(key: String, type: SystemSettingEntity.Type, value: String): SystemSettingEntity.SettingValue = try {
        when (type) {
            SystemSettingEntity.Type.BOOLEAN -> SystemSettingEntity.SettingValue.BooleanValue(value.toBooleanStrict())

            SystemSettingEntity.Type.INT -> SystemSettingEntity.SettingValue.IntValue(value.toInt())
            SystemSettingEntity.Type.INT_LIST -> SystemSettingEntity.SettingValue.IntListValue(objectMapper.readValue(value))

            SystemSettingEntity.Type.LONG -> SystemSettingEntity.SettingValue.LongValue(value.toLong())
            SystemSettingEntity.Type.LONG_LIST -> SystemSettingEntity.SettingValue.LongListValue(objectMapper.readValue(value))

            SystemSettingEntity.Type.FLOAT -> SystemSettingEntity.SettingValue.FloatValue(value.toFloat())
            SystemSettingEntity.Type.FLOAT_LIST -> SystemSettingEntity.SettingValue.FloatListValue(objectMapper.readValue(value))

            SystemSettingEntity.Type.DOUBLE -> SystemSettingEntity.SettingValue.DoubleValue(value.toDouble())
            SystemSettingEntity.Type.DOUBLE_LIST -> SystemSettingEntity.SettingValue.DoubleListValue(objectMapper.readValue(value))

            SystemSettingEntity.Type.STRING -> SystemSettingEntity.SettingValue.StringValue(value)
            SystemSettingEntity.Type.STRING_LIST -> SystemSettingEntity.SettingValue.StringListValue(objectMapper.readValue(value))

            SystemSettingEntity.Type.DATE -> SystemSettingEntity.SettingValue.DateValue(LocalDate.parse(value))
            SystemSettingEntity.Type.TIME -> SystemSettingEntity.SettingValue.TimeValue(LocalTime.parse(value))
            SystemSettingEntity.Type.INSTANT -> SystemSettingEntity.SettingValue.InstantValue(Instant.parse(value))

            SystemSettingEntity.Type.AUDIT_LOG_ENTITY_REFERENCE -> SystemSettingEntity.SettingValue.AuditLogEntityReferenceValue(auditLogService.get(value.toLong()))
            SystemSettingEntity.Type.DEVICE_BOOKING_ENTITY_REFERENCE -> SystemSettingEntity.SettingValue.DeviceBookingEntityReferenceValue(deviceBookingService.get(value.toLong()))
            SystemSettingEntity.Type.DEVICE_CATEGORY_ENTITY_REFERENCE -> SystemSettingEntity.SettingValue.DeviceCategoryEntityReferenceValue(deviceCategoryService.get(value.toLong()))
            SystemSettingEntity.Type.DEVICE_ENTITY_REFERENCE -> SystemSettingEntity.SettingValue.DeviceEntityReferenceValue(deviceService.get(value.toLong()))
            SystemSettingEntity.Type.EXCEPTION_ENTITY_REFERENCE -> SystemSettingEntity.SettingValue.ExceptionEntityReferenceValue(exceptionService.get(value.toLong()))
            SystemSettingEntity.Type.FAQ_ENTITY_REFERENCE -> SystemSettingEntity.SettingValue.FAQEntityReferenceValue(faqService.get(value.toLong()))
            SystemSettingEntity.Type.LOCKER_ENTITY_REFERENCE -> SystemSettingEntity.SettingValue.LockerEntityReferenceValue(lockerService.get(value.toLong()))
            SystemSettingEntity.Type.ROLE_ENTITY_REFERENCE -> SystemSettingEntity.SettingValue.RoleEntityReferenceValue(roleService.get(value.toLong()))
            SystemSettingEntity.Type.ROOM_BOOKING_ENTITY_REFERENCE -> SystemSettingEntity.SettingValue.RoomBookingEntityReferenceValue(roomBookingService.get(value.toLong()))
            SystemSettingEntity.Type.SUPPORT_TICKET_ENTITY_REFERENCE -> SystemSettingEntity.SettingValue.SupportTicketEntityReferenceValue(supportTicketService.get(value.toLong()))
            SystemSettingEntity.Type.SYSTEM_SETTING_ENTITY_REFERENCE -> SystemSettingEntity.SettingValue.SystemSettingEntityReferenceValue(get(value.toLong()))
            SystemSettingEntity.Type.TEMPLATE_ENTITY_REFERENCE -> SystemSettingEntity.SettingValue.TemplateEntityReferenceValue(templateService.get(value.toLong()))
            SystemSettingEntity.Type.USER_ENTITY_REFERENCE -> SystemSettingEntity.SettingValue.UserEntityReferenceValue(userService.get(value.toLong()))
        }
    } catch (exception: Exception) {
        throw BadRequestException("Error parsing system setting with key '$key' -> ${exception.message}")
    }

    override fun create(request: CreateSystemSettingRequest): SystemSettingEntity {
        //Will throw if parsing fails
        val parsed = parse(
            request.key,
            request.type,
            request.value
        )

        return super.create(request)
    }

    override fun patch(id: Long, request: PatchSystemSettingRequest): SystemSettingEntity {
        val original = get(id)

        //Will throw if parsing fails
        val parsed = parse(
            request.key ?: original.key,
            request.type ?: original.type,
            request.value ?: original.value
        )

        return super.patch(id, request)
    }

}