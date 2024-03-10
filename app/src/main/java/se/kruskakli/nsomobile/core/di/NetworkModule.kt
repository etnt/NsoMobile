package se.kruskakli.nsomobile.core.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module


val networkModule = module {
    single {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                    //namingStrategy = JsonNamingStrategy.SnakeCase
                })
            }
            //install(Logging) {
            //    logger = Logger.SIMPLE
            //}
            defaultRequest {
                header(HttpHeaders.Accept, "application/yang-data+json")
                // Add other headers here...
            }
        }
    }
}