package de.csw.turtle.api.repository

import de.csw.turtle.api.entity.GeneralTemplateEntity

interface GeneralTemplateRepository : CRUDRepository<GeneralTemplateEntity> {

    fun findByName(name: String): GeneralTemplateEntity?
    fun findByType(type: GeneralTemplateEntity.Type): GeneralTemplateEntity?

}