package org.kfd.reflecta_backend.controllers

import jakarta.validation.Valid
import org.kfd.reflecta_backend.database.entities.User
import org.kfd.reflecta_backend.dto.user.responses.UserResponseDto
import org.kfd.reflecta_backend.dto.user.requests.UserUpdateDto
import org.kfd.reflecta_backend.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/me")
class UserController(
    private val userService: UserService,
) {

    private fun mapToUserDto(user: User) = UserResponseDto(
        username = user.username,
        email = user.email,
        avatar = user.avatar,
        notificationFrequency = user.userSettings!!.notificationFrequency,
        allowStatisticsNotify = user.userSettings!!.allowStatisticsNotify,
        cachedReportsAmount = user.userSettings!!.cachedReportsAmount
    )


    @GetMapping
    fun getUser(authentication: Authentication) : ResponseEntity<UserResponseDto> {
        val userId = authentication.principal as Long
        val user = userService.getUser(userId)
        return ResponseEntity.ok(mapToUserDto(user))
    }

    @PutMapping
    fun updateUser(
        authentication: Authentication,
        @RequestBody @Valid requestBody: UserUpdateDto
    ) : ResponseEntity<UserResponseDto> {
        val userId = authentication.principal as Long
        val user = userService.updateUser(userId, requestBody)
        return ResponseEntity.ok(mapToUserDto(user))
    }

    @PutMapping("/avatar")
    fun updateAvatar(
        authentication: Authentication,
        @RequestParam avatar: MultipartFile
    ) : ResponseEntity<UserResponseDto> {
        val userId = authentication.principal as Long
        val user = userService.updateAvatar(userId, avatar)
        return ResponseEntity.ok(mapToUserDto(user))
    }

    @DeleteMapping
    fun deleteUser(
        authentication: Authentication
    ) : ResponseEntity<Void> {
        val userId = authentication.principal as Long
        userService.deleteUser(userId)
        return ResponseEntity.noContent().build()
    }
}