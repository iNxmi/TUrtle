package de.csw.turtle.api.entity

import com.samskivert.mustache.Mustache
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "templates")
data class TemplateEntity(

    @Column(unique = true)
    var name: String,

    @Column(columnDefinition = "TEXT")
    var description: String,

    @Column(columnDefinition = "TEXT")
    var content: String

) : CRUDEntity() {

    fun getCompiledContent(id: Long, variables: Map<String, Any?>): String {
        val template = Mustache.compiler().compile(content)
        return template.execute(variables)
    }

}