package com.csw.turtleapi.api.v1.repository

import com.csw.turtleapi.api.v1.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>
