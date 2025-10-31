package de.csw.turtle.v1.repository

import de.csw.turtle.v1.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository : JpaRepository<User, Long> {

    fun findByUsername(username: String): Optional<User>

    fun deleteByUsername(username: String)

}
