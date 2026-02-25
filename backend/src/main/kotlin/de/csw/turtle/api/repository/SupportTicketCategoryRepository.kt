package de.csw.turtle.api.repository

import de.csw.turtle.api.entity.SupportTicketCategoryEntity

interface SupportTicketCategoryRepository : CRUDRepository<SupportTicketCategoryEntity> {

    fun findByName(name: String): SupportTicketCategoryEntity?

}