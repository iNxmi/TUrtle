package de.csw.turtle.api.repository

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class RawSqlRepository(
    @PersistenceContext
    private val entityManager: EntityManager
) {

    @Transactional(readOnly = true)
    fun executeReadOnlyQuery(query: String): Any? {
        val query = entityManager.createNativeQuery(query)
        val resultList = query.resultList

        return when {
            resultList.isEmpty() -> null
            resultList.size == 1 -> resultList[0]
            else -> resultList
        }
    }

}