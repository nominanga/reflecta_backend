package org.kfd.reflecta_backend.services

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.kfd.reflecta_backend.configs.JwtProperties
import org.kfd.reflecta_backend.database.entities.User
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtService (private val jwtProperties: JwtProperties){

    private val accessSecret by lazy {
        Keys.hmacShaKeyFor(jwtProperties.access.secret.toByteArray() )
    }
    private val refreshSecret by lazy {
        Keys.hmacShaKeyFor(jwtProperties.refresh.secret.toByteArray() )
    }

    fun generateToken(userId: Long, isAccess: Boolean): String {
        val now = System.currentTimeMillis()
        val (expiration, secret) = if (isAccess) {
            jwtProperties.access.expirationTime to accessSecret
        } else {
            jwtProperties.refresh.expirationTime to refreshSecret
        }

        return Jwts.builder()
            .claim("id", userId.toString())
            .issuedAt(Date(now))
            .expiration(Date(now + expiration))
            .signWith(secret)
            .compact()
    }

    fun generateAccessToken(userId: Long): String = generateToken(userId, isAccess = true)

    fun generateRefreshToken(userId: Long): String = generateToken(userId, isAccess = false)

    fun parseToken(token: String, isAccess: Boolean): Claims? {
        return try {
            Jwts.parser()
                .verifyWith(if (isAccess) accessSecret else refreshSecret)
                .build()
                .parseSignedClaims(token)
                .payload
        } catch (e: Exception) {
            null
        }
    }

    fun extractUserId(token: String, isAccess: Boolean): Long? {
        val claims = parseToken(token, isAccess)
        return claims?.get("id")?.toString()?.toLong()
    }

    fun refreshAccessToken(refreshToken: String): String? {
        val userId = extractUserId(refreshToken, isAccess = false)
        return if (userId != null) {
            generateAccessToken(userId)
        } else {
            null
        }
    }
}