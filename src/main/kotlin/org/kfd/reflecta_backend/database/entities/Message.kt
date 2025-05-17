package org.kfd.reflecta_backend.database.entities

import jakarta.persistence.*
import org.kfd.reflecta_backend.enums.MessageSender

@Entity
@Table(name = "messages")
class Message(
    @Column(nullable = false)
    var text: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val sender: MessageSender = MessageSender.AI,

    @ManyToOne(optional = false)
    @JoinColumn(name = "thread_id")
    val noteThread: NoteThread,
) : AbstractEntity() {
}