package de.csw.turtle.api.repository

import de.csw.turtle.api.entity.SupportTicketUrgencyEntity

interface SupportTicketUrgencyRepository : CRUDRepository<SupportTicketUrgencyEntity> {

    fun findByName(name: String): SupportTicketUrgencyEntity?

}