package se.kruskakli.nsomobile.syscounters.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.call.receive
import io.ktor.client.request.basicAuth
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.HttpResponse
import io.ktor.http.URLProtocol
import io.ktor.http.appendPathSegments
import io.ktor.http.path
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import se.kruskakli.nsomobile.core.data.NetworkRepository


interface SysCountersRepository {
    suspend fun getSysCounters(
        host: String,
        port: String,
        user: String,
        password: String,
        onSuccess: suspend (HttpResponse) -> Unit,
        onError: (String?) -> Unit = {}
    ): Unit
}


class SysCountersRepositoryImpl(
    private val networkRepository: NetworkRepository
) : SysCountersRepository {
    //private val client: HttpClient by inject()

    override suspend fun getSysCounters(
        host: String,
        port: String,
        user: String,
        password: String,
        onSuccess: suspend (HttpResponse) -> Unit,
        onError: (String?) -> Unit
    ): Unit {
        networkRepository.apiCall(
            host,
            port,
            user,
            password,
            "restconf/data/tailf-ncs:metric/sysadmin/counter",
            onSuccess,
            onError
        )
    }
}