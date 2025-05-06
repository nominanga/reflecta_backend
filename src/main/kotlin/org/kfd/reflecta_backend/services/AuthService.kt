package org.kfd.reflecta_backend.services

import org.kfd.reflecta_backend.dto.UserDto
import org.kfd.reflecta_backend.dto.auth.requests.LoginRequest
import org.kfd.reflecta_backend.dto.auth.requests.RegistrationRequest
import org.kfd.reflecta_backend.dto.auth.responses.AuthResponse
import org.kfd.reflecta_backend.exceptions.ConflictException
import org.kfd.reflecta_backend.exceptions.InvalidCredentialsException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val jwtService: JwtService,
    private val userService: UserService,
) {

    private val encoder: PasswordEncoder = BCryptPasswordEncoder(12)

    fun register(request: RegistrationRequest): AuthResponse {
        if (userService.existsUserByEmail(request.email)) {
            throw ConflictException("User with email ${request.email} already exists")
        }
        val hashedPassword: String = encoder.encode(request.password)
        request.password = hashedPassword

        val user = userService.createUser(request)
        val userId = user.id

        return AuthResponse(
            jwtService.generateToken(userId, true),
            jwtService.generateToken(userId, false)
        )
    }

    fun login(request: LoginRequest): AuthResponse {
        val user = userService.getUserByEmail(request.email)

        if (encoder.matches(request.password, user.password)) {
            return AuthResponse(
                jwtService.generateToken(user.id, true),
                jwtService.generateToken(user.id, false)
            )
        } else {
            throw InvalidCredentialsException("Wrong password")
        }
    }


}