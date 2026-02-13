package de.csw.turtle.api.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import de.csw.turtle.api.Settings
import de.csw.turtle.api.entity.SystemSettingEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.repository.SystemSettingRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime

@Service
class SystemSettingService(
    override val repository: SystemSettingRepository,
    val objectMapper: ObjectMapper
) : CRUDService<SystemSettingEntity>() {

    final inline fun <reified T> getTyped(setting: Settings): T {
        val entity = getByKey(setting.key)
        val result = parse(entity.key, entity.type, entity.value)

        if (result !is T)
            throw IllegalStateException("System setting with key '$setting' expected type '${T::class.simpleName}', but was type ${result::class.simpleName}'")

        return result
    }

    fun getByKeyOrNull(key: String): SystemSettingEntity? = repository.findByKey(key)
    fun getByKey(key: String): SystemSettingEntity = getByKeyOrNull(key)
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
            SystemSettingEntity.Type.DURATION -> Duration.parse(value)
        }
    } catch (exception: Exception) {
        throw HttpException.BadRequest("Error parsing system setting with key '$key' -> ${exception.message}")
    }

    @Transactional
    fun create(
        key: String,
        type: SystemSettingEntity.Type,
        value: String,
        visibility: SystemSettingEntity.Visibility
    ): SystemSettingEntity {
        //Will throw if parsing fails
        parse(key, type, value)

        val entity = SystemSettingEntity(
            key = key,
            type = type,
            value = value,
            visibility = visibility
        )

        return repository.save(entity)
    }

    @Transactional
    fun patch(
        id: Long,
        key: String? = null,
        type: SystemSettingEntity.Type? = null,
        value: String? = null,
        visibility: SystemSettingEntity.Visibility? = null
    ): SystemSettingEntity {
        val entity = repository.findById(id).get()

        //Will throw if parsing fails
        parse(
            key ?: entity.key,
            type ?: entity.type,
            value ?: entity.value
        )

        key?.let { entity.key = it }
        type?.let { entity.type = it }
        value?.let { entity.value = it }
        visibility?.let { entity.visibility = it }

        return repository.save(entity)
    }

}