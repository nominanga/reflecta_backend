package org.kfd.reflecta_backend.dto.deepseek

data class MessagePayload(
    val role: String,
    val content: String
)

data class ChatRequest(
    val model: String,
    val messages: List<MessagePayload>,
    val stream: Boolean = false
)

data class ChatResponse(
    val choices: List<Choice>
)

data class Choice(
    val message: AIMessage
)

data class AIMessage(
    val role: String,
    val content: String
)