package de.csw.turtle.api.repository

import de.csw.turtle.api.entity.TokenEntity

interface TokenRepository : CRUDRepository<TokenEntity> {

    fun findByUuid(uuid: String): TokenEntity?

}