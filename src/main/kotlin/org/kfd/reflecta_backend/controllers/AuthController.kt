package org.kfd.reflecta_backend.controllers

import org.kfd.reflecta_backend.services.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/register")
    fun register() {

    }

    @PostMapping("/login")
    fun login() {

    }

    @PostMapping("/logout")
    fun logout() {

    }

    @PostMapping("/refresh")
    fun refreshAccessToken() {

    }

}