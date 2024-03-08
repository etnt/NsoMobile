package se.kruskakli.nsomobile.syscounters.data

import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.basicAuth
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.URLProtocol
import io.ktor.http.appendPathSegments
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.GlobalContext.get


interface SysCountersRepository {
    suspend fun getSysCounters(): HttpResponse
}

class SysCountersRepositoryImpl() : SysCountersRepository, KoinComponent {
    private val client: HttpClient by inject()

    override suspend fun getSysCounters(): HttpResponse {

        // See: https://ktor.io/docs/request.html
        return client.get {
            url {
                protocol = URLProtocol.HTTP
                host = "192.168.1.231"
                port = 9080
                appendPathSegments("restconf", "data", "tailf-ncs:metric", "sysadmin", "counter")
            }
            basicAuth("admin", "admin")
            headers {
                append("Accept", "application/yang-data+json")
            }
        }
    }
}