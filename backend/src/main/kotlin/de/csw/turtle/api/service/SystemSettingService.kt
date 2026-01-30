package de.csw.turtle.api.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import de.csw.turtle.api.dto.create.CreateSystemSettingRequest
import de.csw.turtle.api.dto.get.GetSystemSettingResponse
import de.csw.turtle.api.dto.patch.PatchSystemSettingRequest
import de.csw.turtle.api.entity.SystemSettingEntity
import de.csw.turtle.api.exception.HttpException
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

    val objectMapper: ObjectMapper,

    val auditLogService: AuditLogService,
    val deviceBookingService: DeviceBookingService,
    val deviceCategoryService: DeviceCategoryService,
    val deviceService: DeviceService,
    val exceptionService: ExceptionService,
    val faqService: FAQService,
    val lockerService: LockerService,
    val roleService: RoleService,
    val roomBookingService: RoomBookingService,
    val supportTicketService: SupportTicketService,
    val templateService: TemplateService,
    val userService: UserService
) : CRUDService<SystemSettingEntity, CreateSystemSettingRequest, GetSystemSettingResponse, PatchSystemSettingRequest>("SystemSetting") {

    final inline fun <reified T> getTyped(key: String): T {
        val entity = getByKey(key)
        val result = parse(entity.key, entity.type, entity.value)

        if (result !is T)
            TODO("implement exception")

        return result
    }

    fun getByKeyOrNull(key: String): SystemSettingEntity? = repository.findByKey(key)
    fun getByKey(key: String): SystemSettingEntity = repository.findByKey(key)
        ?: throw HttpException.NotFound("System setting with key '$key' not found.")

    fun parse(key: String, type: SystemSettingEntity.Type, value: String): Any = try {
        when (type) {
            SystemSettingEntity.Type.BOOLEAN -> value.toBooleanStrict()
            SystemSettingEntity.Type.INT -> value.toInt()
            SystemSettingEntity.Type.INT_LIST -> objectMapper.readValue(value)
            SystemSettingEntity.Type.LONG -> value.toLong()
            SystemSettingEntity.Type.LONG_LIST -> objectMapper.readValue(value)
            SystemSettingEntity.Type.FLOAT -> value.toFloat()
            SystemSettingEntity.Type.FLOAT_LIST -> objectMapper.readValue(value)
            SystemSettingEntity.Type.DOUBLE -> value.toDouble()
            SystemSettingEntity.Type.DOUBLE_LIST -> objectMapper.readValue(value)
            SystemSettingEntity.Type.STRING -> value
            SystemSettingEntity.Type.STRING_LIST -> objectMapper.readValue(value)
            SystemSettingEntity.Type.DATE -> LocalDate.parse(value)
            SystemSettingEntity.Type.TIME -> LocalTime.parse(value)
            SystemSettingEntity.Type.INSTANT -> Instant.parse(value)
            SystemSettingEntity.Type.AUDIT_LOG_ENTITY_REFERENCE -> auditLogService.get(value.toLong())
            SystemSettingEntity.Type.DEVICE_BOOKING_ENTITY_REFERENCE -> deviceBookingService.get(value.toLong())
            SystemSettingEntity.Type.DEVICE_CATEGORY_ENTITY_REFERENCE -> deviceCategoryService.get(value.toLong())
            SystemSettingEntity.Type.DEVICE_ENTITY_REFERENCE -> deviceService.get(value.toLong())
            SystemSettingEntity.Type.EXCEPTION_ENTITY_REFERENCE -> exceptionService.get(value.toLong())
            SystemSettingEntity.Type.FAQ_ENTITY_REFERENCE -> faqService.get(value.toLong())
            SystemSettingEntity.Type.LOCKER_ENTITY_REFERENCE -> lockerService.get(value.toLong())
            SystemSettingEntity.Type.ROLE_ENTITY_REFERENCE -> roleService.get(value.toLong())
            SystemSettingEntity.Type.ROOM_BOOKING_ENTITY_REFERENCE -> roomBookingService.get(value.toLong())
            SystemSettingEntity.Type.SUPPORT_TICKET_ENTITY_REFERENCE -> supportTicketService.get(value.toLong())
            SystemSettingEntity.Type.SYSTEM_SETTING_ENTITY_REFERENCE -> get(value.toLong())
            SystemSettingEntity.Type.TEMPLATE_ENTITY_REFERENCE -> templateService.get(value.toLong())
            SystemSettingEntity.Type.USER_ENTITY_REFERENCE -> userService.get(value.toLong())
        }
    } catch (exception: Exception) {
        throw HttpException.BadRequest("Error parsing system setting with key '$key' -> ${exception.message}")
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