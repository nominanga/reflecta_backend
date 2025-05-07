package org.kfd.reflecta_backend.controllers

import jakarta.validation.Valid
import org.kfd.reflecta_backend.dto.auth.requests.LoginRequest
import org.kfd.reflecta_backend.dto.auth.requests.RegistrationRequest
import org.kfd.reflecta_backend.dto.auth.responses.AuthResponse
import org.kfd.reflecta_backend.services.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/register")
    fun register(
        @RequestBody @Valid requestBody: RegistrationRequest
    ) : ResponseEntity<AuthResponse> {
        val tokenPair = authService.register(requestBody)
        return ResponseEntity.ok(tokenPair)
    }

    @PostMapping("/login")
    fun login(
        @RequestBody @Valid requestBody: LoginRequest
    ) : ResponseEntity<AuthResponse> {
        val tokenPair = authService.login(requestBody)
        return ResponseEntity.ok(tokenPair)
    }

    @PostMapping("/logout")
    fun logout(authentication: Authentication) : ResponseEntity<Void> {
        val userId = authentication.principal as Long
        authService.logout(userId)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/refresh")
    fun refreshAccessToken(
        @RequestParam("token", required = true) refreshToken: String
    ) : ResponseEntity<AuthResponse> {
        val tokenPair = authService.refresh(refreshToken)
        return ResponseEntity.ok(tokenPair)
    }

}