package kfd.reflecta.backend.database.repositories

import kfd.reflecta.backend.database.entities.Note
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface NoteRepository : JpaRepository<Note, Long> {
    fun findNotesByUserId(userId: Long, pageable: Pageable): Page<Note>
}