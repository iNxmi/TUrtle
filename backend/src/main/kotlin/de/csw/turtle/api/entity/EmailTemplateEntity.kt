package de.csw.turtle.api.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "email_templates")
class EmailTemplateEntity(

    @Column(unique = true)
    var name: String,

    @Column(columnDefinition = "TEXT")
    var description: String,

    var subject: String,

    @Column(columnDefinition = "TEXT")
    var content: String

) : CRUDEntity()