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

//    @Transactional(readOnly = true)
//    fun executeReadOnlyQuery(query: String): Any? {
//        val query = entityManager.createNativeQuery(query)
//        val resultList = query.resultList
//
//        return when {
//            resultList.isEmpty() -> null
//            resultList.size == 1 -> resultList[0]
//            else -> resultList
//        }
//    }

    @Transactional(readOnly = true)
    fun executeSingle(query: String): Any? = entityManager.createNativeQuery(query).singleResult

    @Transactional(readOnly = true)
    fun executeListQuery(query: String): List<Any?> = entityManager.createNativeQuery(query).resultList

    @Transactional(readOnly = true)
    fun executeMapQuery(query: String): Map<Any?, Any?> {
        val results = executeListQuery(query)

        return results.associate {
            val row = it as Array<*>
            val key = row[0]

            val value = when (row[1]) {
                is Number -> (row[1] as Number).toLong()
                else -> row[1]
            }

            key to value
        }
    }

}