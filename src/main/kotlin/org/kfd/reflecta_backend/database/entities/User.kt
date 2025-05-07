package org.kfd.reflecta_backend.database.entities

import jakarta.persistence.*

@Entity
@Table(name = "`users`")
class User(
    @Column(nullable = false)
    var username: String,

    @Column(unique=true, nullable = false)
    val email: String,

    @Column(nullable = false)
    var password: String,

) : AbstractEntity() {

    @Column(unique=true)
    var avatar: String = "/media/avatars/default.png"

    @OneToOne(mappedBy = "user", cascade = [(CascadeType.ALL)], orphanRemoval = true)
    var userSettings: UserSettings? = null

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_theme_chosen",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "theme_id")],
    )
    val themes: MutableSet<Theme> = mutableSetOf()

    @OneToMany(
        mappedBy = "user",
        cascade = [(CascadeType.ALL)],
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    val notes: MutableList<Note> = mutableListOf()

    @OneToMany(
        mappedBy = "user",
        cascade = [(CascadeType.ALL)],
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    val dailyStatistics: MutableList<DailyStatistics> = mutableListOf()

    @OneToMany(
        mappedBy = "user",
        cascade = [(CascadeType.ALL)],
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    val reports: MutableList<Report> = mutableListOf()
}