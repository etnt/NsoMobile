package se.kruskakli.nsomobile.debug.inet.data

import se.kruskakli.nsomobile.core.data.ApiOperation
import se.kruskakli.nsomobile.core.data.NetworkRepository

interface NsoInetRepository {

    suspend fun getNsoInet(
        host : String,
        port : String,
        user : String,
        password : String
    ): ApiOperation<Inet>
}

class NsoInetRepositoryImpl(
    private val networkRepository: NetworkRepository
) : NsoInetRepository {

    override suspend fun getNsoInet(
        host : String,
        port : String,
        user : String,
        password : String
    ): ApiOperation<Inet> {
        return networkRepository.apiCall<Inet>(
            host,
            port,
            user,
            password,
            "restconf/data/nso-dbg%3Anso-dbg/beam-state/inet",
            Inet.serializer(),
            emptyMap()
        )
    }
}