package se.kruskakli.nsomobile.alarms.data

import se.kruskakli.nso.data.alarms.NsoAlarmList
import se.kruskakli.nsomobile.core.data.ApiOperation
import se.kruskakli.nsomobile.core.data.NetworkRepository

interface AlarmsRepository {
    suspend fun getAlarmList(
        host: String,
        port: String,
        user: String,
        password: String
    ): ApiOperation<NsoAlarmList>
}

class AlarmsRepositoryImpl(
    private val networkRepository: NetworkRepository
) : AlarmsRepository {

    override suspend fun getAlarmList(
        host: String,
        port: String,
        user: String,
        password: String
    ): ApiOperation<NsoAlarmList> {
        return networkRepository.apiCall<NsoAlarmList>(
            host,
            port,
            user,
            password,
            "restconf/data/tailf-ncs-alarms:alarms/alarm-list",
            NsoAlarmList.serializer()
        )
    }
}