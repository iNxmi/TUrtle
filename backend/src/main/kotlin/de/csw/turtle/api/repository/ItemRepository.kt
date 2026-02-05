package de.csw.turtle.api.repository

import de.csw.turtle.api.entity.ItemEntity

interface ItemRepository : CRUDRepository<ItemEntity> {
    fun findByName(name: String): ItemEntity?
}