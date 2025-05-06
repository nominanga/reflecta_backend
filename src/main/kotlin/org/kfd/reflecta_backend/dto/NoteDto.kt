package org.kfd.reflecta_backend.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class NoteDto(
    val title: String? = null,
    @field:NotBlank(message = "Note content should not be empty")
    val body: String,
    @field:NotNull
    val userId: Long
)
