package de.csw.turtle.api.repository

import de.csw.turtle.api.entity.DeviceEntity
import org.springframework.data.jpa.repository.JpaRepository

interface DeviceRepository: JpaRepository<DeviceEntity, Long>