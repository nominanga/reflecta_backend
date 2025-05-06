package org.kfd.reflecta_backend.services

import org.kfd.reflecta_backend.database.entities.Note
import org.kfd.reflecta_backend.database.repositories.NoteRepository
import org.kfd.reflecta_backend.dto.NoteDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class NoteService(
    private val noteRepository: NoteRepository,
    private val userService: UserService,
) {
    fun getNotesByUserId(userId: Long, page: Int, size: Int): Page<Note> {
        val pageable: Pageable = PageRequest.of(page, size)
        return noteRepository.findNotesByUserId(userId, pageable)
    }

    fun createNote(noteDto: NoteDto): Note {

//        if (noteDto.userId)
//
//
//        val note = Note(
//            title = noteDto.title,
//            body = noteDto.body,
//
//        )

        return noteRepository.save(note)
    }

}