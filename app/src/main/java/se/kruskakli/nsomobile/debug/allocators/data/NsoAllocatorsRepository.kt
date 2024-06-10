package se.kruskakli.nsomobile.debug.allocators.data

import se.kruskakli.nsomobile.core.data.ApiOperation
import se.kruskakli.nsomobile.core.data.NetworkRepository

interface NsoAllocatorsRepository {

    suspend fun getNsoAllocators(
        host : String,
        port : String,
        user : String,
        password : String
    ): ApiOperation<Allocators>
}

class NsoAllocatorsRepositoryImpl(
    private val networkRepository: NetworkRepository
) : NsoAllocatorsRepository {

    override suspend fun getNsoAllocators(
        host : String,
        port : String,
        user : String,
        password : String
    ): ApiOperation<Allocators> {
        return networkRepository.apiCall<Allocators>(
            host,
            port,
            user,
            password,
            "restconf/data/nso-dbg%3Anso-dbg/beam-state/memory/stats/live/allocators",
            Allocators.serializer(),
            emptyMap()
        )
    }
}