package de.csw.turtle.api.entity

import com.samskivert.mustache.Mustache
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "templates")
class TemplateEntity(

    @Column(unique = true)
    var name: String,

    @Column(columnDefinition = "TEXT")
    var description: String,

    @Column(columnDefinition = "TEXT")
    var content: String

) : CRUDEntity() {

    fun getCompiledContent(): String {
        //TODO add variables to insert into template
        val variables: Map<String, Any?> = mapOf()

        val template = Mustache.compiler().compile(content)
        return template.execute(variables)
    }

}