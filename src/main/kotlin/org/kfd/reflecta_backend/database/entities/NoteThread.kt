package org.kfd.reflecta_backend.database.entities

import jakarta.persistence.*

@Entity
@Table(name = "note_threads")
class NoteThread(
    @OneToOne
    @JoinColumn(name = "note_id")
    val note: Note
) : AbstractEntity() {
    @OneToMany(
        mappedBy = "noteThread",
        cascade = [(CascadeType.ALL)],
        orphanRemoval = true
    )
    val messages: MutableList<Message> = mutableListOf()
}