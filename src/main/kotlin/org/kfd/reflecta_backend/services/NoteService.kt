package org.kfd.reflecta_backend.services

import org.kfd.reflecta_backend.database.entities.Message
import org.kfd.reflecta_backend.database.entities.Note
import org.kfd.reflecta_backend.database.entities.NoteThread
import org.kfd.reflecta_backend.database.repositories.NoteRepository
import org.kfd.reflecta_backend.dto.NoteDto
import org.kfd.reflecta_backend.enums.MessageSender
import org.springframework.stereotype.Service

@Service
class NoteService(
    private val noteRepository: NoteRepository,
    private val userService: UserService,
    private val deepSeekApiService: DeepSeekApiService
) {
    fun getNotesByUserId(userId: Long): List<Note> {
        return noteRepository.findNotesByUserId(userId)
    }

    fun createNote(userId: Long, noteDto: NoteDto): Note {
        val body = noteDto.body
        val firstAiResponse = deepSeekApiService.generateReply(body)

        val user = userService.getUser(userId)

        val note = Note(
            title = noteDto.title,
            body = body,
            user
        )

        val noteThread = NoteThread(note)
        val aiMessage = Message(
            text = firstAiResponse,
            sender = MessageSender.AI,
            noteThread = noteThread
        )

        noteThread.messages.add(aiMessage)
        note.noteThread = noteThread

        return noteRepository.save(note)
    }

}