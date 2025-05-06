package org.kfd.reflecta_backend.database.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.kfd.reflecta_backend.database.entities.AbstractEntity


@Entity
@Table(name = "themes")
class Theme(
    @Column(unique = true, nullable = false)
    val name: String,

    @Column(name = "is_default")
    val isDefault: Boolean = false
) : AbstractEntity() {
}