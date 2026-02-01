package de.csw.turtle.api.repository

import de.csw.turtle.api.entity.EmailTemplateEntity

interface EmailTemplateRepository : CRUDRepository<EmailTemplateEntity> {

    fun findByName(name: String): EmailTemplateEntity?

}