package de.csw.turtle.api.service.locker

import de.csw.turtle.api.dto.create.CreateLockerRequest
import de.csw.turtle.api.dto.get.GetLockerResponse
import de.csw.turtle.api.dto.patch.PatchLockerRequest
import de.csw.turtle.api.entity.LockerEntity
import de.csw.turtle.api.mapper.LockerMapper
import de.csw.turtle.api.repository.LockerRepository
import de.csw.turtle.api.service.CRUDService
import org.springframework.stereotype.Service

@Service
class LockerService(
    override val repository: LockerRepository,
    override val mapper: LockerMapper,
) : CRUDService<LockerEntity, CreateLockerRequest, GetLockerResponse, PatchLockerRequest>("Locker") {

    fun getByIndex(index: Int) = repository.findByIndex(index)

}