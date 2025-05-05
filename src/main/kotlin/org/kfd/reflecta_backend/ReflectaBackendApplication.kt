package org.kfd.reflecta_backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ReflectaBackendApplication

fun main(args: Array<String>) {
    runApplication<ReflectaBackendApplication>(*args)
}
