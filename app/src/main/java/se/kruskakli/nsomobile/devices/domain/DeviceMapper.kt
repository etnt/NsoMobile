package se.kruskakli.nsomobile.devices.domain

import se.kruskakli.nsomobile.devices.data.NcsDevice


fun NcsDevice.toDeviceUi(): DeviceUi {
    val nsoCommitQueue = DeviceUi.CommitQueueUI(
        commitQueue.queueLength.toString()
    )
    val deviceState = DeviceUi.StateUI(
        state.operState,
        state.transactionMode,
        state.adminState
    )
    val nsoAlarmSummary = DeviceUi.TailfNcsAlarmsAlarmSummaryUI(
        alarmSummary.indeterminates.toString(),
        alarmSummary.criticals.toString(),
        alarmSummary.majors.toString(),
        alarmSummary.minors.toString(),
        alarmSummary.warnings.toString()
    )
    return DeviceUi(
        name = name,
        lastConnected = lastConnected,
        address = address,
        port = port.toString(),
        authgroup = authgroup,
        commitQueue = nsoCommitQueue,
        state = deviceState,
        alarmSummary = nsoAlarmSummary
    )
}