package de.csw.turtle.api.repository

import de.csw.turtle.api.entity.ExternalIdentityEntity

interface ExternalIdentityRepository: CRUDRepository<ExternalIdentityEntity> {

    fun findByIssuerAndSubject(issuer: String, subject: String): ExternalIdentityEntity?

}