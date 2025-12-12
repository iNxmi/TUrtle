package de.csw.turtle.api.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "system_settings")
data class SystemSettingEntity(

    @Column(unique = true)
    var key: String,

    var value: String

) : CRUDEntity()