package org.kfd.reflecta_backend.database.repositories

import org.kfd.reflecta_backend.database.entities.Message
import org.springframework.data.jpa.repository.JpaRepository

interface MessageRepository : JpaRepository<Message, Long> {
    fun getMessagesByNoteThreadId(threadId: Long): List<Message>
}