package de.csw.turtle.api.repository

import de.csw.turtle.api.entity.EmailTemplateEntity
import de.csw.turtle.api.entity.EmailTemplateEntity.Type

interface EmailTemplateRepository : CRUDRepository<EmailTemplateEntity> {

    fun existsByType(type: Type): Boolean
    fun findByName(name: String): EmailTemplateEntity?
    fun findByType(type: Type): EmailTemplateEntity?

}