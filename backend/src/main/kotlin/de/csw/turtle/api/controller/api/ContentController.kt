package de.csw.turtle.api.controller.api

import de.csw.turtle.api.service.SystemSettingService
import de.csw.turtle.api.service.TemplateService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/content")
class ContentController(
    val templateService: TemplateService,
    val systemSettingService: SystemSettingService
) {

    //TODO implement proper null checks

    @GetMapping("/imprint")
    fun imprint(): ResponseEntity<String> {
        val id = systemSettingService.getByKeyOrNull("template.imprint")!!.value.toLong()
        val content = templateService.get(id).getCompiledContent()
        return ResponseEntity.ok(content)
    }

}