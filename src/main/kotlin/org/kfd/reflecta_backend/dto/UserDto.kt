package org.kfd.reflecta_backend.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UserDto(
    val username: String? = null,
    val email: String? = null,
    val password: String?,
    val avatar: String? = null,
)
