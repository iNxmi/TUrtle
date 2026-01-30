package de.csw.turtle.api.controller.api

import de.csw.turtle.api.entity.TemplateEntity
import de.csw.turtle.api.service.SystemSettingService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/content")
class ContentController(
    private val systemSettingService: SystemSettingService
) {

    private fun getContent(key: String): String {
        val template = systemSettingService.getTyped<TemplateEntity>(key)
        return template.getCompiledContent()
    }

    private fun getResponse(key: String) = ResponseEntity.ok(getContent(key))

    @GetMapping("/imprint")
    fun imprint(): ResponseEntity<String> = getResponse("template.imprint")

    @GetMapping("/tos")
    fun tos(): ResponseEntity<String> = getResponse("template.tos")

    @GetMapping("/about")
    fun about(): ResponseEntity<String> = getResponse("template.about")

    @GetMapping("/gdpr")
    fun gdpr(): ResponseEntity<String> = getResponse("template.gdpr")

    @GetMapping("/contact")
    fun contact(): ResponseEntity<String> = getResponse("template.contact")

}