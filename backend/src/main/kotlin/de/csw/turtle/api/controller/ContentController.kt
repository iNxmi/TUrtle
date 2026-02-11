package de.csw.turtle.api.controller

import de.csw.turtle.api.entity.GeneralTemplateEntity.Type
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.service.GeneralTemplateService
import de.csw.turtle.api.service.ThymeleafService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.thymeleaf.context.Context

@RestController
@RequestMapping("/api/content")
class ContentController(
    private val generalTemplateService: GeneralTemplateService,
    private val thymeleafService: ThymeleafService
) {

    private fun getResponse(type: Type): ResponseEntity<String> {
        val template = generalTemplateService.getByType(type)
            ?: throw HttpException.NotFound()

        val context = Context()

        val content = thymeleafService.getRendered(template.content, context)
        return ResponseEntity.ok(content)
    }

    @GetMapping("/imprint")
    fun imprint(): ResponseEntity<String> = getResponse(Type.IMPRINT)

    @GetMapping("/tos")
    fun tos(): ResponseEntity<String> = getResponse(Type.TERMS_OF_SERVICE)

    @GetMapping("/about")
    fun about(): ResponseEntity<String> = getResponse(Type.ABOUT)

    @GetMapping("/gdpr")
    fun gdpr(): ResponseEntity<String> = getResponse(Type.GENERAL_DATA_PROTECTION_REGULATION)

    @GetMapping("/contact")
    fun contact(): ResponseEntity<String> = getResponse(Type.CONTACT)

}