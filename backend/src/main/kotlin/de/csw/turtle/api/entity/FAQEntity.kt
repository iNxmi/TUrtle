package de.csw.turtle.api.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "faq")
class FAQEntity(

    @Column(unique = true)
    var name: String,

    @Column(columnDefinition = "TEXT")
    var content: String

) : CRUDEntity()

