package se.kruskakli.nsomobile.debug.ets.data

import se.kruskakli.nsomobile.core.data.ApiOperation
import se.kruskakli.nsomobile.core.data.NetworkRepository

interface NsoEtsRepository {

    suspend fun getNsoEts(
        host : String,
        port : String,
        user : String,
        password : String
    ): ApiOperation<Ets>
}

class NsoEtsRepositoryImpl(
    private val networkRepository: NetworkRepository
) : NsoEtsRepository {

    override suspend fun getNsoEts(
        host : String,
        port : String,
        user : String,
        password : String
    ): ApiOperation<Ets> {
        return networkRepository.apiCall<Ets>(
            host,
            port,
            user,
            password,
            "restconf/data/nso-dbg%3Anso-dbg/beam-state/ets-tables",
            Ets.serializer(),
            emptyMap()
        )
    }
}