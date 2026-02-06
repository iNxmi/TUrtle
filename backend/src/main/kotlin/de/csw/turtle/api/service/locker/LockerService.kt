package de.csw.turtle.api.service.locker

import de.csw.turtle.api.entity.LockerEntity
import de.csw.turtle.api.repository.LockerRepository
import de.csw.turtle.api.service.CRUDService
import org.springframework.stereotype.Service

@Service
class LockerService(
    override val repository: LockerRepository
) : CRUDService<LockerEntity>("Locker") {

    fun getByIndex(index: Int) = repository.findByIndex(index)

}