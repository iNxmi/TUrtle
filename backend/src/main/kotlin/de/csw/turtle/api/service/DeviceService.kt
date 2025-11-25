package de.csw.turtle.api.service

import de.csw.turtle.api.dto.create.CreateDeviceRequest
import de.csw.turtle.api.dto.patch.PatchDeviceRequest
import de.csw.turtle.api.entity.DeviceEntity
import de.csw.turtle.api.exception.exceptions.device.DeviceNotFoundException
import de.csw.turtle.api.repository.DeviceRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
class DeviceService(
    private val repository: DeviceRepository,
    private val deviceCategoryService: DeviceCategoryService,
    private val lockerService: LockerService
) {

    @Transactional
    fun create(request: CreateDeviceRequest): DeviceEntity {
        //TODO implement checks

        val category = deviceCategoryService.get(request.categoryId)!!
        val locker = lockerService.get(request.lockerId)!!

        val entity = DeviceEntity(
            name = request.name,
            description = request.description,
            inventoryId = request.inventoryId,
            category = category,
            locker = locker
        )
        return repository.save(entity)
    }

    //TODO make it not query for hidden fields based on dto (only be able to sort by dto fields)
    fun getPaginated(request: PageRequest) = repository.findAll(request)
    fun get(id: Long) = repository.findById(id).getOrNull()

    @Transactional
    fun update(id: Long, request: PatchDeviceRequest): DeviceEntity {
        val entity = repository.findById(id).getOrNull()
            ?: throw DeviceNotFoundException(id)

        request.name?.let { entity.name = it }
        request.description?.let { entity.description = it }
        request.inventoryId?.let { entity.inventoryId = it }
        request.categoryId?.let { entity.category = deviceCategoryService.get(it)!! }
        request.lockerId?.let { entity.locker = lockerService.get(it)!! }

        return repository.save(entity)
    }

    @Transactional
    fun delete(id: Long) {
        val entity = repository.findById(id).getOrNull()
            ?: throw DeviceNotFoundException(id)

        repository.delete(entity)
    }

}