package de.csw.turtle.api.repository

import de.csw.turtle.api.entity.LockerEntity
import org.springframework.data.jpa.repository.JpaRepository

interface LockerRepository : JpaRepository<LockerEntity, Long> {

    fun findByIndex(index: Int): LockerEntity?

}