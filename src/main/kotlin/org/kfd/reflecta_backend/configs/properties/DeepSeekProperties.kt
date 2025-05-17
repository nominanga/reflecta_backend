package org.kfd.reflecta_backend.configs.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "deepseek")
data class DeepSeekProperties(
    var apiKey: String = ""
)
