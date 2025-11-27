package de.csw.turtle.api.repository

import de.csw.turtle.api.entity.SupportTicketEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SupportTicketRepository : CRUDRepository<SupportTicketEntity>
