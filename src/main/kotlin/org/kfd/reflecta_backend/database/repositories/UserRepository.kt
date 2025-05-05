package kfd.reflecta.backend.database.repositories

import kfd.reflecta.backend.database.entities.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun existsUserByEmail(email: String): Boolean
}