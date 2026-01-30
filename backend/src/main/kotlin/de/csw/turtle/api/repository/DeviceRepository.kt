package de.csw.turtle.api.repository

import de.csw.turtle.api.entity.DeviceEntity

interface DeviceRepository : CRUDRepository<DeviceEntity> {
    fun findByName(name: String): DeviceEntity?
}