package org.kfd.reflecta_backend.services

import com.fasterxml.jackson.databind.ObjectMapper
import org.kfd.reflecta_backend.dto.auth.responses.AuthResponse
import org.kfd.reflecta_backend.exceptions.InvalidCredentialsException
import org.kfd.reflecta_backend.exceptions.UnauthorizedException
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class RedisTokenService (
    private val objectMapper: ObjectMapper,
    private val redisTemplate: StringRedisTemplate,
    private val jwtService: JwtService,
) {
    fun generateTokens(userId: Long): AuthResponse {
        val key = "user:$userId"
        val accessToken = jwtService.generateAccessToken(userId)
        val refreshToken = jwtService.generateRefreshToken(userId)
        val value = mapOf(
            "token" to refreshToken,
            "expiresAt" to (System.currentTimeMillis() + jwtService.refreshTokenExpirationTime()) / 1000
        )

        redisTemplate.opsForValue().set(
            key,
            objectMapper.writeValueAsString(value),
            Duration.ofSeconds(jwtService.refreshTokenExpirationTime() / 1000)
        )

        return AuthResponse(
            accessToken,
            refreshToken
        )
    }

    fun removeToken(userId: Long) {
        redisTemplate.delete("user:$userId")
    }

    fun refreshTokens(refreshToken: String): AuthResponse {
        val userId = jwtService.extractUserId(refreshToken, false)
            ?: throw UnauthorizedException("Authentication has expired")
        val key = "user:$userId"
        val stored = redisTemplate.opsForValue().get(key) ?: throw UnauthorizedException("Authentication has expired")

        val data = objectMapper.readValue(stored, Map::class.java)
        if (refreshToken == data["token"]) {
            removeToken(userId)
            return generateTokens(userId)
        } else {
            throw InvalidCredentialsException("Invalid token")
        }
    }
}