package org.kfd.reflecta_backend.services

import org.kfd.reflecta_backend.database.entities.Message
import org.kfd.reflecta_backend.database.repositories.MessageRepository
import org.springframework.stereotype.Service

@Service
class MessageService(
    private val messageRepository: MessageRepository
) {
    fun getThreadMessages(threadId: Long): List<Message> {
        return messageRepository.getMessagesByNoteThreadId(threadId)
    }

    
}