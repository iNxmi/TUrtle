package de.csw.turtle.api.repository

import de.csw.turtle.api.entity.ContentTemplateEntity

interface ContentTemplateRepository : CRUDRepository<ContentTemplateEntity> {

    fun findByName(name: String): ContentTemplateEntity?
    fun findByType(type: ContentTemplateEntity.Type): ContentTemplateEntity?

}