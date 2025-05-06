package org.kfd.reflecta_backend.services

import jakarta.persistence.EntityNotFoundException
import org.kfd.reflecta_backend.database.entities.User
import org.kfd.reflecta_backend.database.entities.UserSettings
import org.kfd.reflecta_backend.database.repositories.UserRepository
import org.kfd.reflecta_backend.dto.UserSettingsDto
import org.kfd.reflecta_backend.dto.auth.requests.RegistrationRequest
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {

    fun createUser(userCredentials: RegistrationRequest): User {

        val user = User(
            username = userCredentials.username,
            email = userCredentials.email,
            password = userCredentials.password
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

    fun existsUserByEmail(email: String): Boolean = userRepository.existsByEmail(email)

    fun getUserByEmail(email: String): User {
        val user = userRepository.findByEmail(email) ?: throw EntityNotFoundException("User with email: $email not found")
        return user
    }

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