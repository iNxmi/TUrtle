package de.csw.turtle.api.repository

import de.csw.turtle.api.entity.SystemSettingEntity

interface SystemSettingRepository : CRUDRepository<SystemSettingEntity> {

    fun findByKey(key: String): SystemSettingEntity?

}