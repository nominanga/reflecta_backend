package org.kfd.reflecta_backend.database.entities

import jakarta.persistence.*

@Entity
@Table(name = "notes")
class Note(
    @Column
    var title: String?,

    @Column
    var body: String,

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    val user: User

) : AbstractEntity() {

    @Column(name = "is_favorite")
    var isFavorite: Boolean = false

    @OneToOne(mappedBy = "note", cascade = [CascadeType.ALL], orphanRemoval = true)
    var noteThread: NoteThread? = null

    @ManyToMany
    @JoinTable(
        name = "note_theme_included",
        joinColumns = [JoinColumn(name = "note_id")],
        inverseJoinColumns = [JoinColumn(name = "theme_id")]
    )
    val themes: MutableSet<Theme> = mutableSetOf()
}