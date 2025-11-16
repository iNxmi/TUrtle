package de.csw.turtle.api.repository

import org.springframework.context.annotation.Bean
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository
import org.springframework.stereotype.Component
import javax.sql.DataSource

@Component
class PersistentTokenRepositoryComponent {

    @Bean
    fun persistentTokenRepository(dataSource: DataSource): PersistentTokenRepository {
        val repo = JdbcTokenRepositoryImpl()
        repo.setDataSource(dataSource)
        return repo
    }

}