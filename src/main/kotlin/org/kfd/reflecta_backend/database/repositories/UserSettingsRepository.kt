package org.kfd.reflecta_backend.database.repositories

import org.kfd.reflecta_backend.database.entities.UserSettings
import org.springframework.data.jpa.repository.JpaRepository

interface UserSettingsRepository : JpaRepository<UserSettings, Long> {
}