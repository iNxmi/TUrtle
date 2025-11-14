package de.csw.turtle.api.repository

import de.csw.turtle.api.entity.ExceptionEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ExceptionRepository : JpaRepository<ExceptionEntity, Long>