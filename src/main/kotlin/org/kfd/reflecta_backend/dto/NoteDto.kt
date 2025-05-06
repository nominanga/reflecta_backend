package org.kfd.reflecta_backend.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class NoteDto(
    val title: String? = null,
    @field:NotBlank(message = "Note content cannot be empty")
    val body: String
)
