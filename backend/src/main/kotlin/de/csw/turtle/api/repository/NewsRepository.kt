package de.csw.turtle.api.repository

import de.csw.turtle.api.entity.NewsEntity
import org.springframework.data.jpa.repository.JpaRepository

interface NewsRepository: JpaRepository<NewsEntity, Long>