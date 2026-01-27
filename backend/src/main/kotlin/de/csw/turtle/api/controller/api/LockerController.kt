package de.csw.turtle.api.controller.api

import de.csw.turtle.api.controller.CreateController
import de.csw.turtle.api.controller.DeleteController
import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.controller.PatchController
import de.csw.turtle.api.dto.create.CreateLockerRequest
import de.csw.turtle.api.dto.get.GetLockerResponse
import de.csw.turtle.api.dto.patch.PatchLockerRequest
import de.csw.turtle.api.entity.LockerEntity
import de.csw.turtle.api.mapper.LockerMapper
import de.csw.turtle.api.service.locker.LockerService
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/lockers")
class LockerController(
    override val endpoint: String = "/api/lockers",
    override val service: LockerService,
    override val mapper: LockerMapper
) : CreateController<LockerEntity, CreateLockerRequest, GetLockerResponse>,
    GetController<LockerEntity, GetLockerResponse>,
    PatchController<LockerEntity, PatchLockerRequest, GetLockerResponse>,
    DeleteController<LockerEntity> {

    @PreAuthorize("hasAuthority('MANAGE_LOCKERS')")
    override fun create(request: CreateLockerRequest) = super.create(request)

    @PreAuthorize("hasAuthority('MANAGE_LOCKERS')")
    override fun patch(id: Long, request: PatchLockerRequest) = super.patch(id, request)

    @PreAuthorize("hasAuthority('MANAGE_LOCKERS')")
    override fun delete(id: Long) = super.delete(id)

}