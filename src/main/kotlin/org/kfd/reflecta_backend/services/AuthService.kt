package org.kfd.reflecta_backend.services

import org.kfd.reflecta_backend.dto.auth.requests.LoginRequest
import org.kfd.reflecta_backend.dto.auth.requests.RegistrationRequest
import org.kfd.reflecta_backend.dto.auth.responses.AuthResponse
import org.kfd.reflecta_backend.exceptions.ConflictException
import org.kfd.reflecta_backend.exceptions.InvalidCredentialsException
import org.kfd.reflecta_backend.exceptions.UnauthorizedException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val redisTokenService: RedisTokenService,
    private val userService: UserService,
    private val jwtService: JwtService,
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

        return redisTokenService.generateTokens(userId)
    }

    fun login(request: LoginRequest): AuthResponse {
        val user = userService.getUserByEmail(request.email)

        if (encoder.matches(request.password, user.password)) {
            return redisTokenService.generateTokens(user.id)
        } else {
            throw InvalidCredentialsException("Wrong password")
        }
    }

    fun refresh(refreshToken: String): AuthResponse = redisTokenService.refreshTokens(refreshToken)

    fun logout(accessToken: String) {
        val userId = jwtService.extractUserId(accessToken, true) ?: throw UnauthorizedException("User is unauthorized")
        redisTokenService.removeTokenById(userId)
    }

}