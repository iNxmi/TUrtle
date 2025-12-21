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

    fun getContent(key: String): String {
        val id = systemSettingService.getByKeyOrNull(key)!!.value.toLong()
        val template = templateService.get(id)
        return template.getCompiledContent()
    }

    @GetMapping("/imprint")
    fun imprint() = ResponseEntity.ok(getContent("template.imprint"))

    @GetMapping("/agb")
    fun agb() = ResponseEntity.ok(getContent("template.agb"))

    @GetMapping("/about")
    fun about() = ResponseEntity.ok(getContent("template.about"))

    @GetMapping("/dsgvo")
    fun dsgvo() = ResponseEntity.ok(getContent("template.dsgvo"))

    @GetMapping("/contact")
    fun contact() = ResponseEntity.ok(getContent("template.contact"))

}