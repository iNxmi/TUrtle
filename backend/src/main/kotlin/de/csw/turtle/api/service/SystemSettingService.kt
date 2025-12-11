package de.csw.turtle.api.service

import de.csw.turtle.api.dto.create.CreateSystemSettingRequest
import de.csw.turtle.api.dto.get.GetSystemSettingResponse
import de.csw.turtle.api.dto.patch.PatchSystemSettingRequest
import de.csw.turtle.api.entity.SystemSettingEntity
import de.csw.turtle.api.mapper.SystemSettingMapper
import de.csw.turtle.api.repository.SystemSettingRepository
import org.springframework.stereotype.Service

@Service
class SystemSettingService(
    override val repository: SystemSettingRepository,
    override val mapper: SystemSettingMapper
) : CRUDService<SystemSettingEntity, CreateSystemSettingRequest, GetSystemSettingResponse, PatchSystemSettingRequest>("SystemSetting") {



}