package se.kruskakli.nsomobile.nsopackage.data

import se.kruskakli.nsomobile.alarms.data.AlarmsRepository
import se.kruskakli.nsomobile.alarms.data.NsoAlarmList
import se.kruskakli.nsomobile.core.data.ApiOperation
import se.kruskakli.nsomobile.core.data.NetworkRepository

interface NsoPackageRepository {
    suspend fun getNsoPackages(
        host: String,
        port: String,
        user: String,
        password: String
    ): ApiOperation<NsoPackages>
}

class NsoPackageRepositoryImpl(
    private val networkRepository: NetworkRepository
) : NsoPackageRepository {

    override suspend fun getNsoPackages(
        host: String,
        port: String,
        user: String,
        password: String
    ): ApiOperation<NsoPackages> {
        return networkRepository.apiCall<NsoPackages>(
            host,
            port,
            user,
            password,
            "restconf/data/tailf-ncs:packages",
            NsoPackages.serializer(),
            emptyMap()
        )
    }
}