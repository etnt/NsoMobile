package se.kruskakli.nsomobile.core.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module


val networkModule = module {
    single {
        //HttpClient(OkHttp) {
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
            //level = LogLevel.HEADERS
            //}
            //defaultRequest {
            //    header(HttpHeaders.Accept, "application/yang-data+json")
            //    // Add other headers here...
            //}
        }
    }
}