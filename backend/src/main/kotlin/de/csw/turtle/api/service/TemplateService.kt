package de.csw.turtle.api.service

import com.samskivert.mustache.Mustache
import de.csw.turtle.api.dto.create.CreateTemplateRequest
import de.csw.turtle.api.dto.get.GetTemplateResponse
import de.csw.turtle.api.dto.patch.PatchTemplateRequest
import de.csw.turtle.api.entity.TemplateEntity
import de.csw.turtle.api.mapper.TemplateMapper
import de.csw.turtle.api.repository.TemplateRepository
import org.springframework.stereotype.Service

@Service
class TemplateService(
    override val repository: TemplateRepository,
    override val mapper: TemplateMapper
) : CRUDService<TemplateEntity, CreateTemplateRequest, GetTemplateResponse, PatchTemplateRequest>("Template")