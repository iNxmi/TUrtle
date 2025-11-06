package de.csw.turtle.api.v1.repository

import de.csw.turtle.api.v1.entity.NewsEntity
import org.springframework.data.jpa.repository.JpaRepository

interface NewsRepository: JpaRepository<NewsEntity, Long>