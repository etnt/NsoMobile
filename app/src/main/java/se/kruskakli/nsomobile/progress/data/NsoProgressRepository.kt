package se.kruskakli.nsomobile.progress.data

import se.kruskakli.nsomobile.core.data.ApiOperation
import se.kruskakli.nsomobile.core.data.NetworkRepository

interface NsoProgressRepository {
    suspend fun getNsoProgress(
        host: String,
        port: String,
        user: String,
        password: String
    ): ApiOperation<NsoProgress>
}

class NsoProgressRepositoryImpl(
    private val networkRepository: NetworkRepository
) : NsoProgressRepository {

    override suspend fun getNsoProgress(
        host: String,
        port: String,
        user: String,
        password: String
    ): ApiOperation<NsoProgress> {
        return networkRepository.apiCall<NsoProgress>(
            host,
            port,
            user,
            password,
            "restconf/data/tailf-progress:progress",
            NsoProgress.serializer(),
            mapOf("unhide" to "debug")
        )
    }
}