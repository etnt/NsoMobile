package se.kruskakli.nsomobile.debug.processes.data

import se.kruskakli.nsomobile.core.data.ApiOperation
import se.kruskakli.nsomobile.core.data.NetworkRepository

interface NsoProcessRepository {

    suspend fun getNsoProcesses(
        host : String,
        port : String,
        user : String,
        password : String
    ): ApiOperation<NsoProcesses>
}

class NsoProcessRepositoryImpl(
    private val networkRepository: NetworkRepository
) : NsoProcessRepository {

    override suspend fun getNsoProcesses(
        host : String,
        port : String,
        user : String,
        password : String
    ): ApiOperation<NsoProcesses> {
        return networkRepository.apiCall<NsoProcesses>(
            host,
            port,
            user,
            password,
            "restconf/data/nso-dbg:nso-dbg/beam-state/processes/all",
            NsoProcesses.serializer(),
            emptyMap()
        )
    }
}