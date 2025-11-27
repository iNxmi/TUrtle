package de.csw.turtle.api.repository

import de.csw.turtle.api.entity.LockerEntity
import org.springframework.data.jpa.repository.JpaRepository

interface LockerRepository : CRUDRepository<LockerEntity> {

    fun findByIndex(index: Int): LockerEntity?

}