package se.kruskakli.nsomobile.devices.data

import se.kruskakli.nsomobile.core.data.ApiOperation
import se.kruskakli.nsomobile.core.data.NetworkRepository


interface NsoDevicesRepository {
    suspend fun getNsoDevices(
        host: String,
        port: String,
        user: String,
        password: String
    ): ApiOperation<NsoDevices>
}

class NsoDevicesRepositoryImpl(
    private val networkRepository: NetworkRepository
) : NsoDevicesRepository {

    override suspend fun getNsoDevices(
        host: String,
        port: String,
        user: String,
        password: String
    ): ApiOperation<NsoDevices> {
        return networkRepository.apiCall<NsoDevices>(
            host,
            port,
            user,
            password,
            "restconf/data/tailf-ncs:devices",
            NsoDevices.serializer(),
            mapOf("depth" to "4")
        )
    }
}