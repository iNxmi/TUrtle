package de.csw.turtle.api.controller.crud

import de.csw.turtle.api.dto.create.CreateLockerRequest
import de.csw.turtle.api.dto.get.GetLockerResponse
import de.csw.turtle.api.dto.patch.PatchLockerRequest
import de.csw.turtle.api.entity.LockerEntity
import de.csw.turtle.api.mapper.LockerMapper
import de.csw.turtle.api.repository.LockerRepository
import de.csw.turtle.api.service.LockerService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/resources/lockers")
class LockerController(
    override val service: LockerService,
    override val mapper: LockerMapper
) : CRUDController<LockerEntity, CreateLockerRequest, GetLockerResponse, PatchLockerRequest, LockerRepository, LockerMapper, LockerService>(
    "/api/resources/lockers"
)