package de.csw.turtle.api.entity

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

@Entity
@Table(
    name = "external_identities",
    uniqueConstraints = [UniqueConstraint(columnNames = ["issuer", "subject"])]
)
data class ExternalIdentityEntity(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: UserEntity,

    var issuer: String,

    var subject: String
) : CRUDEntity()