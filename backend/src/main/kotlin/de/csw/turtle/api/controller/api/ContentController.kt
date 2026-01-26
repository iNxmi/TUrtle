package de.csw.turtle.api.controller.api

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
        val template = systemSettingService.getTemplateEntity(key)
        return template.getCompiledContent()
    }

    @GetMapping("/imprint")
    fun imprint(): ResponseEntity<String> = ResponseEntity.ok(getContent("template.imprint"))

    @GetMapping("/tos")
    fun tos(): ResponseEntity<String> = ResponseEntity.ok(getContent("template.tos"))

    @GetMapping("/about")
    fun about(): ResponseEntity<String> = ResponseEntity.ok(getContent("template.about"))

    @GetMapping("/gdpr")
    fun gdpr(): ResponseEntity<String> = ResponseEntity.ok(getContent("template.gdpr"))

    @GetMapping("/contact")
    fun contact(): ResponseEntity<String> = ResponseEntity.ok(getContent("template.contact"))

}