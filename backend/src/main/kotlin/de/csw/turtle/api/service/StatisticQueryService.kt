package de.csw.turtle.api.service

import de.csw.turtle.api.entity.StatisticQueryEntity
import de.csw.turtle.api.entity.StatisticQueryEntity.Type
import de.csw.turtle.api.repository.StatisticQueryRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class StatisticQueryService(
    override val repository: StatisticQueryRepository
) : CRUDService<StatisticQueryEntity>() {

    @Transactional
    fun create(
        name: String,
        description: String,
        query: String,
        type: Type
    ): StatisticQueryEntity {
        val entity = StatisticQueryEntity(
            name = name,
            description = description,
            query = query,
            type = type
        )

        return repository.save(entity)
    }

    @Transactional
    fun patch(
        id: Long,
        name: String? = null,
        description: String? = null,
        query: String? = null,
        type: Type? = null
    ): StatisticQueryEntity {
        val entity = repository.findById(id).get()

        name?.let { entity.name = it }
        description?.let { entity.description = it }
        query?.let { entity.query = it }
        type?.let { entity.type = it }

        return repository.save(entity)
    }

}