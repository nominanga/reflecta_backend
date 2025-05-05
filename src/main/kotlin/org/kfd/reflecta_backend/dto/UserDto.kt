package kfd.reflecta.backend.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UserDto(
    @field:NotBlank(message = "Username cannot be blank")
    val username: String,

    @field:Email(message = "Invalid email format")
    @field:NotBlank(message = "Email cannot be blank")
    val email: String,

    @field:NotBlank(message = "Password cannot be blank")
    val password: String,

    val avatar: String? = null,
)
