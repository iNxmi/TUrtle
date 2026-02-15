package de.csw.turtle.api.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import de.csw.turtle.api.entity.ConfigurationEntity
import de.csw.turtle.api.entity.ConfigurationEntity.Key
import de.csw.turtle.api.entity.ConfigurationEntity.Type
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.repository.ConfigurationRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime

@Service
class ConfigurationService(
    override val repository: ConfigurationRepository,
    val objectMapper: ObjectMapper
) : CRUDService<ConfigurationEntity>() {

    final inline fun <reified T> getTyped(key: Key): T {
        val entity = getByKey(key) ?: throw NoSuchElementException()
        val result = parse(entity.key, entity.type, entity.value)

        if (result !is T)
            throw IllegalStateException("System setting with key '$key' expected type '${T::class.simpleName}', but was type ${result::class.simpleName}'")

        return result
    }

    fun getByKey(key: Key): ConfigurationEntity? = repository.findByKey(key)

    fun parse(key: Key, type: Type, value: String): Any = try {
        when (type) {
            Type.BOOLEAN -> value.toBooleanStrict()

            Type.INT -> value.toInt()
            Type.INT_LIST -> objectMapper.readValue(value)

            Type.LONG -> value.toLong()
            Type.LONG_LIST -> objectMapper.readValue(value)

            Type.FLOAT -> value.toFloat()
            Type.FLOAT_LIST -> objectMapper.readValue(value)

            Type.DOUBLE -> value.toDouble()
            Type.DOUBLE_LIST -> objectMapper.readValue(value)

            Type.STRING -> value
            Type.STRING_LIST -> objectMapper.readValue(value)

            Type.DATE -> LocalDate.parse(value)
            Type.TIME -> LocalTime.parse(value)
            Type.INSTANT -> Instant.parse(value)
            Type.DURATION -> Duration.parse(value)
        }
    } catch (exception: Exception) {
        throw HttpException.BadRequest("Error parsing system setting with key '$key' -> ${exception.message}")
    }

    @Transactional
    fun create(
        key: Key,
        type: Type,
        value: String,
        visibility: ConfigurationEntity.Visibility
    ): ConfigurationEntity {
        //Will throw if parsing fails
        parse(key, type, value)

        val entity = ConfigurationEntity(
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
        key: Key? = null,
        type: Type? = null,
        value: String? = null,
        visibility: ConfigurationEntity.Visibility? = null
    ): ConfigurationEntity {
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