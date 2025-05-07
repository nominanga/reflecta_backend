package org.kfd.reflecta_backend.dto.user.responses

data class UserResponseDto(
    val username: String,
    val email: String,
    val avatar: String,
    val notificationFrequency: Int?,
    val allowStatisticsNotify: Boolean?,
    val cachedReportsAmount: Int?
)
