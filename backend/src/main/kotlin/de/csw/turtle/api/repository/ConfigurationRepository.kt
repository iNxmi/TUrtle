package de.csw.turtle.api.repository

import de.csw.turtle.api.entity.ConfigurationEntity
import de.csw.turtle.api.entity.ConfigurationEntity.Key

interface ConfigurationRepository : CRUDRepository<ConfigurationEntity> {

    fun findByKey(key: Key): ConfigurationEntity?

}