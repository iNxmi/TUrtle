package de.csw.turtle.api.repository

import de.csw.turtle.api.entity.CRUDEntity
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface CRUDRepository<Entity : CRUDEntity> : JpaRepository<Entity, Long>, JpaSpecificationExecutor<Entity>