package se.kruskakli.nsomobile.core.data

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.call.receive
import io.ktor.client.request.basicAuth
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.readText
import io.ktor.http.URLProtocol
import io.ktor.http.path
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface NetworkRepository {
    suspend fun <T> apiCall(
        hostStr: String,
        portStr: String,
        userStr: String,
        passwordStr: String,
        path: String,
        serializer: KSerializer<T>,
        queryParams: Map<String, String>
    ): ApiOperation<T>
}

class NetworkRepositoryImpl : NetworkRepository, KoinComponent {
    private val client: HttpClient by inject()

    suspend inline fun <T> safeApiCall(apiCall: suspend () -> T): ApiOperation<T> {
        return try {
            ApiOperation.Success(data = apiCall())
        } catch (e: Exception) {
            ApiOperation.Failure(exception = e)
        }
    }

    override suspend fun <T> apiCall(
        hostStr: String,
        portStr: String,
        userStr: String,
        passwordStr: String,
        path: String,
        serializer: KSerializer<T>,
        queryParams: Map<String, String>
    ): ApiOperation<T> {

        return safeApiCall {
            // See: https://ktor.io/docs/request.html
            val response: HttpResponse = client.get {
                url {
                    protocol = URLProtocol.HTTP   // FIXME: URLProtocol.HTTPS
                    host = hostStr
                    port = portStr.toInt()
                    path(path)
                    queryParams.forEach { (key, value) ->
                        parameters.append(key, value)
                    }
                }
                basicAuth(userStr, passwordStr)
                headers {
                    append("Accept", "application/yang-data+json")
                }
            }
            val content = response.bodyAsText()
            Log.d("NetworkRepository", "apiCall: content: $content")
            val json = Json { ignoreUnknownKeys = true }
            json.decodeFromString(serializer, content)
        }
    }
}

sealed interface ApiOperation<T> {
    data class Success<T>(val data: T) : ApiOperation<T>
    data class Failure<T>(val exception: Exception) : ApiOperation<T>

    /*
    fun <R> mapSuccess(transform: (T) -> R): ApiOperation<R> {
        return when (this) {
            is Success -> Success(transform(data))
            is Failure -> Failure(exception)
        }
    }
     */

    fun onSuccess(block: (T) -> Unit): ApiOperation<T> {
        if (this is Success) block(data)
        return this
    }

    fun onFailure(block: (Exception) -> Unit): ApiOperation<T> {
        if (this is Failure) block(exception)
        return this
    }
}