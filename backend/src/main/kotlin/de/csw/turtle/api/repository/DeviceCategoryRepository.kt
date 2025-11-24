package de.csw.turtle.api.repository

import de.csw.turtle.api.entity.DeviceCategoryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface DeviceCategoryRepository : JpaRepository<DeviceCategoryEntity, Long>