package de.csw.turtle.api.repository

import de.csw.turtle.api.entity.LockerEntity

interface LockerRepository : CRUDRepository<LockerEntity> {

    fun findByIndex(index: Int): LockerEntity?

}