package org.kfd.reflecta_backend.database.repositories

import org.kfd.reflecta_backend.database.entities.Note
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface NoteRepository : JpaRepository<Note, Long> {
    fun findNotesByUserId(userId: Long): List<Note>
}