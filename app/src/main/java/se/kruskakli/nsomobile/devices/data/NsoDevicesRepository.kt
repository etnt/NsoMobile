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
            "tailf-ncs:devices?depth=4",
            NsoDevices.serializer()
        )
    }
}