package org.kfd.reflecta_backend.exceptions

import jakarta.persistence.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, Any>> {
        val errors = ex.bindingResult.fieldErrors.associate { it.field to (it.defaultMessage ?: "Invalid value") }

        val responseBody = mapOf(
            "message" to "Validation failed",
            "fields" to errors
        )

        return ResponseEntity.badRequest().body(responseBody)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException) : ResponseEntity<Map<String, String?>> {
        return ResponseEntity.badRequest().body(
            mapOf("message" to ex.message)
        )
    }

    @ExceptionHandler(InvalidCredentialsException::class)
    fun handleInvalidCredentialsException(ex: InvalidCredentialsException) : ResponseEntity<Map<String, String?>> {
        return ResponseEntity.badRequest().body(
            mapOf("message" to ex.message)
        )
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(ex: BadRequestException) : ResponseEntity<Map<String, String?>> {
        return ResponseEntity.badRequest().body(
            mapOf("message" to ex.message)
        )
    }


    @ExceptionHandler(ConflictException::class)
    fun handleConflictException(ex: ConflictException) : ResponseEntity<Map<String, String?>> {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
            mapOf("message" to ex.message)
        )
    }

    @ExceptionHandler(UnauthorizedException::class)
    fun handleUnauthorizedException(ex: UnauthorizedException) : ResponseEntity<Map<String, String?>> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
            mapOf("message" to ex.message)
        )
    }

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFoundException(ex: EntityNotFoundException) : ResponseEntity<Map<String, String?>> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            mapOf("message" to ex.message)
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception) : ResponseEntity<Map<String, String?>> {
        ex.printStackTrace()
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            mapOf("message" to ex.message)
        )
    }
}
