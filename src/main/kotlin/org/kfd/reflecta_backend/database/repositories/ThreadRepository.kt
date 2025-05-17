package org.kfd.reflecta_backend.database.repositories

import org.kfd.reflecta_backend.database.entities.NoteThread
import org.springframework.data.jpa.repository.JpaRepository

interface ThreadRepository : JpaRepository<NoteThread, Long> {
}