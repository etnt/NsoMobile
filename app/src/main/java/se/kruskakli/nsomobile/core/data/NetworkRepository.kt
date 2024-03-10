package se.kruskakli.nsomobile.core.data

import io.ktor.client.HttpClient
import io.ktor.client.request.basicAuth
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.HttpResponse
import io.ktor.http.URLProtocol
import io.ktor.http.path
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface NetworkRepository {
    suspend fun apiCall(
        hostStr: String,
        portStr: String,
        userStr: String,
        passwordStr: String,
        path: String,
        onSuccess: suspend (HttpResponse) -> Unit,
        onError: (String?) -> Unit
    ): Unit
}

class NetworkRepositoryImpl : NetworkRepository, KoinComponent {
    override suspend fun apiCall(
        hostStr: String,
        portStr: String,
        userStr: String,
        passwordStr: String,
        path: String,
        onSuccess: suspend (HttpResponse) -> Unit,
        onError: (String?) -> Unit
    ): Unit {
        val client: HttpClient by inject()

        try {
            // See: https://ktor.io/docs/request.html
            val response: HttpResponse = client.get {
                url {
                    protocol = URLProtocol.HTTP   // FIXME: URLProtocol.HTTPS
                    host = hostStr
                    port = portStr.toInt()
                    path(path)
                    //appendPathSegments("restconf", "data", "tailf-ncs:metric", "sysadmin", "counter")
                }
                basicAuth(userStr, passwordStr)
                headers {
                    append("Accept", "application/yang-data+json")
                }
            }
            onSuccess(response)
        } catch (e: Exception) {
            onError(e.message ?: "Unknown error")
        }
    }
}
