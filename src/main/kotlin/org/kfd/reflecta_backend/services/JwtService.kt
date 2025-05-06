package org.kfd.reflecta_backend.services

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.kfd.reflecta_backend.configs.JwtProperties
import org.springframework.stereotype.Service

@Service
class JwtService (private val jwtProperties: JwtProperties){

    private val accessSecret by lazy {
        Keys.hmacShaKeyFor(jwtProperties.access.secret.toByteArray() )
    }
    private val refreshSecret by lazy {
        Keys.hmacShaKeyFor(jwtProperties.refresh.secret.toByteArray() )
    }

    fun generateAccessToken(username: String): String = Jwts.builder()
        .subject(username)
        .signWith(accessSecret)
        .compact()
}