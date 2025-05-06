package org.kfd.reflecta_backend.database.repositories

import org.kfd.reflecta_backend.database.entities.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): User?
}