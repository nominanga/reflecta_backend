package org.kfd.reflecta_backend.database.repositories

import org.kfd.reflecta_backend.database.entities.Report
import org.springframework.data.jpa.repository.JpaRepository

interface ReportRepository : JpaRepository<Report, Long> {
}