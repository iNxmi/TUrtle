package de.csw.turtle.api.service

import de.csw.turtle.api.dto.create.CreateFAQRequest
import de.csw.turtle.api.dto.get.GetFAQResponse
import de.csw.turtle.api.dto.patch.PatchFAQRequest
import de.csw.turtle.api.entity.FAQEntity
import de.csw.turtle.api.mapper.FAQMapper
import de.csw.turtle.api.repository.FAQRepository
import org.springframework.stereotype.Service

@Service
class FAQService(
    override val repository: FAQRepository,
    override val mapper: FAQMapper
) : CRUDService<FAQEntity, CreateFAQRequest, GetFAQResponse, PatchFAQRequest>("FAQ")