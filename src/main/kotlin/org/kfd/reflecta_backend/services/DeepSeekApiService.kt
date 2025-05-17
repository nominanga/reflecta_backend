package org.kfd.reflecta_backend.services

import org.kfd.reflecta_backend.configs.properties.DeepSeekProperties
import org.kfd.reflecta_backend.dto.deepseek.MessagePayload
import org.kfd.reflecta_backend.dto.deepseek.ChatRequest
import org.kfd.reflecta_backend.dto.deepseek.ChatResponse
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class DeepSeekApiService(
    private val props: DeepSeekProperties,
    private val webClientBuilder: WebClient.Builder
) {
    private val webClient = webClientBuilder
        .baseUrl("https://api.deepseek.com")
        .defaultHeader("Content-Type", "application/json")
        .defaultHeader("Authorization", "Bearer ${props.apiKey}")
        .build()

    companion object {
        private const val MODEL = "deepseek-chat"
        private val SYSTEM_MESSAGE = MessagePayload(
            role = "system",
            content = "Ты — внимательный и чуткий личный психолог. " +
                    "Твоя задача — помогать человеку лучше понять самого себя" +
                    ", его эмоции, переживания, амбиции и внутренние конфликты. " +
                    "На основе записей в личном дневнике ты должен проводить глубокий анализ, " +
                    "задавать уточняющие вопросы, помогать выявлять скрытые чувства и причины поведения. " +
                    "Будь поддерживающим, но честным, стимулируй саморефлексию и способствуй личностному росту. " +
                    "Говори мягко, вдумчиво и уважительно.\n"
        )
    }

    fun generateReply(noteText: String): String {
        val messages = listOf(
            SYSTEM_MESSAGE,
            MessagePayload(role = "user", content = noteText)
        )

        val request = ChatRequest(
            model = MODEL,
            messages = messages,
            stream = false
        )

        val response = webClient.post()
            .uri("/chat/completions")
            .bodyValue(request)
            .retrieve()
            .bodyToMono(ChatResponse::class.java)
            .block()

        return response?.choices?.firstOrNull()?.message?.content
            ?: throw IllegalStateException("No AI response from assistant")
    }


}