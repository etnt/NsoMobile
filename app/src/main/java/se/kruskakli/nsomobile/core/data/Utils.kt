package se.kruskakli.nsomobile.core.data

import io.ktor.client.HttpClient
import io.ktor.http.HttpHeaders
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.util.encodeBase64

/*
suspend fun HttpClient.getWithCustomCredentials(url: String, username: String, password: String) {
    val authHeader = "Basic ${"$username:$password".encodeBase64()}"
    get<String>(url) {
        header(HttpHeaders.Authorization, authHeader)
    }
}
*/
