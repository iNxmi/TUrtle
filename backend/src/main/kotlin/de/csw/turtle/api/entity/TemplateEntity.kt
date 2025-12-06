package de.csw.turtle.api.entity

import com.samskivert.mustache.Mustache
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "templates")
data class TemplateEntity(

    @Column(unique = true)
    var name: String,

    @Column(columnDefinition = "TEXT")
    var description: String,

    @Column(columnDefinition = "TEXT")
    var markdown: String

) : CRUDEntity() {

    fun getCompiledContent(id: Long, variables: Map<String, Any?>): String {
        val template = Mustache.compiler().compile(markdown)
        return template.execute(variables)
    }

}