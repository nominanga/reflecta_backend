package org.kfd.reflecta_backend.database.repositories

import org.kfd.reflecta_backend.database.entities.Theme
import org.springframework.data.jpa.repository.JpaRepository

interface ThemeRepository : JpaRepository<Theme, Long> {
}