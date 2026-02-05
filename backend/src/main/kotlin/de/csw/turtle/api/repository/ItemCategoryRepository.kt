package de.csw.turtle.api.repository

import de.csw.turtle.api.entity.ItemCategoryEntity

interface ItemCategoryRepository : CRUDRepository<ItemCategoryEntity> {

    fun findByName(name: String): ItemCategoryEntity?

}