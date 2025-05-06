package org.kfd.reflecta_backend.dto.auth.responses

data class AuthResponse(
    val accessToken: String,
    val refreshToken: String
)
