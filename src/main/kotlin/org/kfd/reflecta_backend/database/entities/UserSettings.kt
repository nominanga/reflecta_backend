package org.kfd.reflecta_backend.database.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.kfd.reflecta_backend.database.entities.AbstractEntity
import org.kfd.reflecta_backend.database.entities.User

@Entity
@Table(name = "user_settings")
class UserSettings(

    @Column(name="notification_frequency")
    var notificationFrequency: Int = 10,

    @Column(name="allow_statistics_notify")
    var allowStatisticsNotify: Boolean = false,

    @Column(name="cached_reports_amount")
    var cachedReportsAmount: Int = 100,

    @OneToOne
    @JoinColumn(name = "user_id")
    val user: User
) : AbstractEntity() {
}