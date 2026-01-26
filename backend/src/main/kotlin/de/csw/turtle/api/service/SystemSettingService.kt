package de.csw.turtle.api.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import de.csw.turtle.api.dto.create.CreateSystemSettingRequest
import de.csw.turtle.api.dto.get.GetSystemSettingResponse
import de.csw.turtle.api.dto.patch.PatchSystemSettingRequest
import de.csw.turtle.api.entity.SystemSettingEntity
import de.csw.turtle.api.exception.BadRequestException
import de.csw.turtle.api.exception.NotFoundException
import de.csw.turtle.api.mapper.SystemSettingMapper
import de.csw.turtle.api.repository.SystemSettingRepository
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime

@Service
class SystemSettingService(
    override val repository: SystemSettingRepository,
    override val mapper: SystemSettingMapper,
    private val objectMapper: ObjectMapper
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