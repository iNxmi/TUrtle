package de.csw.turtle.api.service

import de.csw.turtle.api.dto.create.CreateSupportTicketRequest
import de.csw.turtle.api.dto.get.GetSupportTicketResponse
import de.csw.turtle.api.dto.patch.PatchSupportTicketRequest
import de.csw.turtle.api.entity.SupportTicketEntity
import de.csw.turtle.api.exception.BadRequestException
import de.csw.turtle.api.mapper.SupportTicketMapper
import de.csw.turtle.api.repository.SupportTicketRepository
import org.springframework.stereotype.Service

@Service
class SupportTicketService(
    override val repository: SupportTicketRepository,
    override val mapper: SupportTicketMapper
) : CRUDService<SupportTicketEntity, CreateSupportTicketRequest, GetSupportTicketResponse, PatchSupportTicketRequest>("SupportTicket") {

    private val regex = ("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$").toRegex()
    private val maxSubjectLength = 64
    private val maxDescriptionLength = 2028

    override fun create(request: CreateSupportTicketRequest): SupportTicketEntity {
        if(request.subject.isBlank() || request.subject.length > maxSubjectLength )
            throw BadRequestException("Subject cannot be left blank and cannot be longer than $maxSubjectLength characters.")

        if(request.description.isBlank() || request.description.length > maxDescriptionLength)
            throw BadRequestException("Description cannot be left blank and cannot be longer than $maxDescriptionLength characters.")

        if(!regex.matches(request.email))
            throw BadRequestException("'${request.email}' is not a valid Email Address.")

        return super.create(request)
    }

    override fun patch(id: Long, request: PatchSupportTicketRequest): SupportTicketEntity {
        if(request.subject != null)
            if(request.subject.isBlank() || request.subject.length > maxSubjectLength )
                throw BadRequestException("Subject cannot be left blank and cannot be longer than $maxSubjectLength characters.")

        if(request.description != null)
            if(request.description.isBlank() || request.description.length > maxDescriptionLength)
                throw BadRequestException("Description cannot be left blank and cannot be longer than $maxDescriptionLength characters.")

        if(request.email != null)
            if(!regex.matches(request.email))
                throw BadRequestException("'${request.email}' is not a valid Email Address.")

        return super.patch(id, request)
    }
}