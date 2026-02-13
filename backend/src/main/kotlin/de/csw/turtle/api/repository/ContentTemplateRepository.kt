package de.csw.turtle.api.repository

import de.csw.turtle.api.entity.ContentTemplateEntity
import de.csw.turtle.api.entity.ContentTemplateEntity.Type

interface ContentTemplateRepository : CRUDRepository<ContentTemplateEntity> {

    fun existsByType(type: Type): Boolean
    fun findByName(name: String): ContentTemplateEntity?
    fun findByType(type: Type): ContentTemplateEntity?

}