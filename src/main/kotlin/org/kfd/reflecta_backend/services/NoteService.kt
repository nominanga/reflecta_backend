package kfd.reflecta.backend.services

import kfd.reflecta.backend.database.entities.Note
import kfd.reflecta.backend.database.entities.User
import kfd.reflecta.backend.database.repositories.NoteRepository
import kfd.reflecta.backend.database.repositories.UserRepository
import kfd.reflecta.backend.dto.NoteDto
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