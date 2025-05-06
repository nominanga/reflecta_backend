package org.kfd.reflecta_backend.database.repositories

import org.kfd.reflecta_backend.database.entities.Thread
import org.springframework.data.jpa.repository.JpaRepository

interface ThreadRepository : JpaRepository<Thread, Long> {
}