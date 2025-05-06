package org.kfd.reflecta_backend.database.repositories

import org.kfd.reflecta_backend.database.entities.DailyStatistics
import org.springframework.data.jpa.repository.JpaRepository

interface DailyStatisticsRepository : JpaRepository<DailyStatistics, Long> {
}