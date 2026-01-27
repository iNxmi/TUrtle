package de.csw.turtle.api.controller.api

import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.controller.PatchController
import de.csw.turtle.api.dto.get.GetSystemSettingResponse
import de.csw.turtle.api.dto.patch.PatchSystemSettingRequest
import de.csw.turtle.api.entity.SystemSettingEntity
import de.csw.turtle.api.mapper.SystemSettingMapper
import de.csw.turtle.api.service.SystemSettingService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/system-settings")
class SystemSettingController(
    override val service: SystemSettingService,
    override val mapper: SystemSettingMapper
) : GetController<SystemSettingEntity, GetSystemSettingResponse>,
    PatchController<SystemSettingEntity, PatchSystemSettingRequest, GetSystemSettingResponse> {



    }