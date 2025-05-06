package org.kfd.reflecta_backend.services

import jakarta.persistence.EntityNotFoundException
import org.kfd.reflecta_backend.database.entities.Note
import org.kfd.reflecta_backend.database.entities.User
import org.kfd.reflecta_backend.database.entities.UserSettings
import org.kfd.reflecta_backend.database.repositories.NoteRepository
import org.kfd.reflecta_backend.dto.UserDto
import org.kfd.reflecta_backend.database.repositories.UserRepository
import org.kfd.reflecta_backend.dto.UserSettingsDto
import org.kfd.reflecta_backend.exceptions.ConflictException
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {

    fun registerUser(userDto: UserDto): User {

        if (userRepository.existsUserByEmail(userDto.email)) {
            throw ConflictException("User with this email already exists")
        }

        val user = User(
            username = userDto.username,
            email = userDto.email,
            password = userDto.password
        )

        user.userSettings = UserSettings(
            user = user
        )

        return userRepository.save(user)
    }

    fun getUser(id: Long): User {
        val user = userRepository.findById(id).orElseThrow {
            EntityNotFoundException("User with id: $id not found")
        }
        return user
    }

    fun deleteUser(id: Long) {
        val user = getUser(id)
        userRepository.delete(user)
    }

    fun updatePassword(id: Long, newPassword: String): User {
        val user = getUser(id)

        user.password = newPassword
        return userRepository.save(user)
    }

    fun existsUserByEmail(email: String): Boolean = userRepository.existsUserByEmail(email)

    fun updateAvatar(id: Long, avatar: String): User {
        val user = getUser(id)

        user.avatar = avatar
        return userRepository.save(user)
    }

    fun updateUserSettings(id: Long, settingsDto: UserSettingsDto): User {
        val user = getUser(id)
        user.userSettings?.apply {
            settingsDto.sendToEmail?.let { sendToEmail = it }
            settingsDto.cachedReportsAmount?.let { cachedReportsAmount = it }
            settingsDto.allowStatisticsNotify?.let { allowStatisticsNotify = it }
            settingsDto.notificationFrequency?.let { notificationFrequency = it }
        }
        return userRepository.save(user)
    }

    fun getUserSettings(id: Long): UserSettings? {
        val user = getUser(id)
        return user.userSettings
    }

}