package de.csw.turtle.api.repository

import de.csw.turtle.api.entity.TemplateEntity

interface TemplateRepository : CRUDRepository<TemplateEntity> {

    fun findByName(name: String): TemplateEntity?

}