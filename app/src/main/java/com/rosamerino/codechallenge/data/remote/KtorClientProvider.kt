package com.rosamerino.codechallenge.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlin.getValue

object KtorClientProvider {
    private const val BASE_URL = "https://api.themoviedb.org/3/"

    // NOTE: Hardcoding tokens is only done here for demonstration purposes in this code challenge.
    // I would never store a token in source code in production, but would use secure secret management instead.
    private const val BEARER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhYmVlZDQzNzlkNjZhN2I5ZDNhY2QyNjQ4NzM5ZTAyNyIsIm5iZiI6MTc1NDY1NTUzOC4wMjksInN1YiI6IjY4OTVlYjMyNTBiMjEyNDhjZjkyMjdhMSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.uetYNxBT7yTysWW1yBxHa2sTL3wivz7P5pT9vOSlTwc"

    val client: HttpClient by lazy {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
            defaultRequest {
                url(BASE_URL)
                headers.append("Authorization", "Bearer $BEARER_TOKEN")
                headers.append("Accept", "application/json")
            }
        }
    }
}
