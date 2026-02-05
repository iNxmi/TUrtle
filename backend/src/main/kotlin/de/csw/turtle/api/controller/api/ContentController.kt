package de.csw.turtle.api.controller.api

import de.csw.turtle.api.Settings
import de.csw.turtle.api.service.GeneralTemplateService
import de.csw.turtle.api.service.SystemSettingService
import de.csw.turtle.api.service.ThymeleafService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/content")
class ContentController(
    private val systemSettingService: SystemSettingService,
    private val generalTemplateService: GeneralTemplateService,
    private val thymeleafService: ThymeleafService
) {

    private fun getResponse(setting: Settings): ResponseEntity<String> {
        val templateId = systemSettingService.getTyped<Long>(setting)
        val template = generalTemplateService.get(templateId)
        val content = template.getCompiledContent(thymeleafService)
        return ResponseEntity.ok(content)
    }

    @GetMapping("/imprint")
    fun imprint(): ResponseEntity<String> = getResponse(Settings.CONTENT_TEMPLATE_IMPRINT)

    @GetMapping("/tos")
    fun tos(): ResponseEntity<String> = getResponse(Settings.CONTENT_TEMPLATE_TOS)

    @GetMapping("/about")
    fun about(): ResponseEntity<String> = getResponse(Settings.CONTENT_TEMPLATE_ABOUT)

    @GetMapping("/gdpr")
    fun gdpr(): ResponseEntity<String> = getResponse(Settings.CONTENT_TEMPLATE_GDPR)

    @GetMapping("/contact")
    fun contact(): ResponseEntity<String> = getResponse(Settings.CONTENT_TEMPLATE_CONTACT)

}