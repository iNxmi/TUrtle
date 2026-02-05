package de.csw.turtle.api.repository

import de.csw.turtle.api.entity.ItemCategoryEntity

interface DeviceCategoryRepository : CRUDRepository<ItemCategoryEntity> {

    fun findByName(name: String): ItemCategoryEntity?

}