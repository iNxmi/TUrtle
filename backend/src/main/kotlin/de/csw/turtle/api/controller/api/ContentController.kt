package de.csw.turtle.api.controller.api

import de.csw.turtle.api.entity.GeneralTemplateEntity
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
    private val thymeleafService: ThymeleafService
) {

    private fun getResponse(key: String): ResponseEntity<String> {
        val template = systemSettingService.getTyped<GeneralTemplateEntity>(key)
        val content = template.getCompiledContent(thymeleafService)
        return ResponseEntity.ok(content)
    }

    @GetMapping("/imprint")
    fun imprint(): ResponseEntity<String> = getResponse("content.template.imprint")

    @GetMapping("/tos")
    fun tos(): ResponseEntity<String> = getResponse("content.template.tos")

    @GetMapping("/about")
    fun about(): ResponseEntity<String> = getResponse("content.template.about")

    @GetMapping("/gdpr")
    fun gdpr(): ResponseEntity<String> = getResponse("content.template.gdpr")

    @GetMapping("/contact")
    fun contact(): ResponseEntity<String> = getResponse("content.template.contact")

}