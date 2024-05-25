package se.kruskakli.nsomobile.debug.inet.data

import se.kruskakli.nsomobile.core.data.ApiOperation
import se.kruskakli.nsomobile.core.data.NetworkRepository

interface NsoInetRepository {

    suspend fun getNsoInet(
        host : String,
        port : String,
        user : String,
        password : String
    ): ApiOperation<NsoInet>
}

class NsoInetRepositoryImpl(
    private val networkRepository: NetworkRepository
) : NsoInetRepository {

    override suspend fun getNsoInet(
        host : String,
        port : String,
        user : String,
        password : String
    ): ApiOperation<NsoInet> {
        return networkRepository.apiCall<NsoInet>(
            host,
            port,
            user,
            password,
            "restconf/data/nso-dbg%3Anso-dbg/beam-state/inet",
            NsoInet.serializer(),
            emptyMap()
        )
    }
}