package org.kfd.reflecta_backend.database.entities

import jakarta.persistence.*
import org.kfd.reflecta_backend.database.entities.AbstractEntity
import org.kfd.reflecta_backend.database.entities.Message
import org.kfd.reflecta_backend.database.entities.Note

@Entity
@Table(name = "threads")
class Thread(
    @OneToOne
    @JoinColumn(name = "note_id")
    val note: Note
) : AbstractEntity() {
    @OneToMany(
        mappedBy = "thread",
        cascade = [(CascadeType.ALL)],
        orphanRemoval = true
    )
    val messages: MutableList<Message> = mutableListOf()
}