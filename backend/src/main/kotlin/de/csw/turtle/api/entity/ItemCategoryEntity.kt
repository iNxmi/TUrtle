package de.csw.turtle.api.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "item_categories")
class ItemCategoryEntity(

    @Column(unique = true)
    var name: String,

    @OneToMany(mappedBy = "category")
    val items: MutableSet<ItemEntity> = mutableSetOf()

) : CRUDEntity()