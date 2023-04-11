package com.example

import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import io.ktor.client.plugins.logging.*
import kotlinx.serialization.Serializable

@Serializable
data class DiscordWebhookRequest(val content: String)

suspend fun main() {
    val discordWebhookUrl = "https://discord.com/api/webhooks/1095341093220003913/ABbhdRefY4bWFtdyU0QX-Cl7owCftEU6PvU6CGWalWIwdk4DH_GG7IIxbGP6rDqoYuMD"
    val message = "Hello, I've sent this from my Ktor app!"

    val client = HttpClient(Apache) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
        install(Logging)
    }

    val requestBody = DiscordWebhookRequest(message)

    val response: HttpResponse = client.post(discordWebhookUrl) {
        contentType(ContentType.Application.Json)
        setBody(requestBody)
    }

    println(response)
    client.close()
}
