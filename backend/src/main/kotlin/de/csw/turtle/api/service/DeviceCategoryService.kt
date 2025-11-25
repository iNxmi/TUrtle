package de.csw.turtle.api.service

import de.csw.turtle.api.dto.create.CreateDeviceCategoryRequest
import de.csw.turtle.api.dto.create.CreateDeviceRequest
import de.csw.turtle.api.dto.patch.PatchDeviceCategoryRequest
import de.csw.turtle.api.dto.patch.PatchDeviceRequest
import de.csw.turtle.api.entity.DeviceCategoryEntity
import de.csw.turtle.api.entity.DeviceEntity
import de.csw.turtle.api.exception.exceptions.device.DeviceNotFoundException
import de.csw.turtle.api.exception.exceptions.device.category.DeviceCategoryNotFoundException
import de.csw.turtle.api.repository.DeviceCategoryRepository
import de.csw.turtle.api.repository.DeviceRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
class DeviceCategoryService(
    private val repository: DeviceCategoryRepository
) {

    @Transactional
    fun create(request: CreateDeviceCategoryRequest): DeviceCategoryEntity {
        val entity = DeviceCategoryEntity( request.name)
        return repository.save(entity)
    }

    //TODO make it not query for hidden fields based on dto (only be able to sort by dto fields)
    fun getPaginated(request: PageRequest) = repository.findAll(request)
    fun get(id: Long) = repository.findById(id).getOrNull()

    @Transactional
    fun update(id: Long, request: PatchDeviceCategoryRequest): DeviceCategoryEntity {
        val entity = repository.findById(id).getOrNull()
            ?: throw DeviceCategoryNotFoundException(id)

        request.name?.let { entity.name = it }

        return repository.save(entity)
    }

    @Transactional
    fun delete(id: Long) {
        val entity = repository.findById(id).getOrNull()
            ?: throw DeviceCategoryNotFoundException(id)

        repository.delete(entity)
    }

}