package de.csw.turtle.api.entity

import de.csw.turtle.api.service.ThymeleafService
import jakarta.persistence.*
import org.thymeleaf.context.Context
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime

@Entity
@Table(name = "general_templates")
class GeneralTemplateEntity(

    @Id @GeneratedValue
    override val id: Long = 0,

    @Column(unique = true)
    var name: String,

    @Column(columnDefinition = "TEXT")
    var description: String,

    @Column(columnDefinition = "TEXT")
    var content: String,

    //Instant.MIN will be replaced by createdAt in prePersist()
    override var updatedAt: Instant = Instant.MIN,

    @Column(updatable = false)
    override val createdAt: Instant = Instant.now()

) : CRUDEntity {

    fun getCompiledContent(thymeleafService: ThymeleafService): String {
        //TODO add variables to insert into template
        val context = Context().apply {
            setVariable("time", LocalTime.now())
            setVariable("date", LocalDate.now())
        }

        return thymeleafService.getRendered(content, context)
    }

    @PrePersist
    fun prePersist() {
        updatedAt = createdAt
    }

    @PreUpdate
    fun preUpdate() {
        updatedAt = Instant.now()
    }

    override fun snapshot() = GeneralTemplateEntity(
        id = id,
        name = name,
        description = description,
        content = content,
        updatedAt = updatedAt,
        createdAt = createdAt
    )

}