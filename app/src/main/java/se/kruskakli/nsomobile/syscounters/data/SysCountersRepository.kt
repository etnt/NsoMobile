package se.kruskakli.nsomobile.syscounters.data

import se.kruskakli.nsomobile.core.data.ApiOperation
import se.kruskakli.nsomobile.core.data.NetworkRepository


interface SysCountersRepository {
    suspend fun getSysCounters(
        host: String,
        port: String,
        user: String,
        password: String
    ): ApiOperation<NsoSysCounters>
}


class SysCountersRepositoryImpl(
    private val networkRepository: NetworkRepository
) : SysCountersRepository {

    override suspend fun getSysCounters(
        host: String,
        port: String,
        user: String,
        password: String
    ): ApiOperation<NsoSysCounters> {
        return networkRepository.apiCall<NsoSysCounters>(
            host,
            port,
            user,
            password,
            "restconf/data/tailf-ncs:metric/sysadmin/counter",
            NsoSysCounters.serializer(),
            emptyMap()
        )
    }
}