package de.csw.turtle.api.entity

import de.csw.turtle.api.service.ThymeleafService
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.thymeleaf.context.Context
import java.time.LocalDate
import java.time.LocalTime

@Entity
@Table(name = "general_templates")
class GeneralTemplateEntity(

    @Column(unique = true)
    var name: String,

    @Column(columnDefinition = "TEXT")
    var description: String,

    @Column(columnDefinition = "TEXT")
    var content: String

) : CRUDEntity() {

    fun getCompiledContent(thymeleafService: ThymeleafService): String {
        //TODO add variables to insert into template
        val context = Context().apply {
            setVariable("time", LocalTime.now())
            setVariable("date", LocalDate.now())
        }

        return thymeleafService.getRendered(content, context)
    }

}