package se.kruskakli.nsomobile.syscounters.data

import se.kruskakli.nsomobile.syscounters.domain.SysCountersUi


fun SysCounters.toUiModel(): SysCountersUi {
    return SysCountersUi(
        transaction = transaction?.toUiModel(),
        serviceConflicts = serviceConflicts?.toUiModel(),
        cdb = cdb?.toUiModel(),
        device = device?.toUiModel(),
        session = session?.toUiModel()
    )
}


// Add this function to the Transaction class
fun Transaction.toUiModel(): SysCountersUi.TransactionUi {
    return SysCountersUi.TransactionUi(
        datastore = datastore.map { it?.toUiModel() }
    )
}

// Add similar functions to the other nested classes
fun Transaction.Datastore.toUiModel(): SysCountersUi.TransactionUi.DatastoreUi {
    return SysCountersUi.TransactionUi.DatastoreUi(
        name = name.toString(),
        commit = commit.toString(),
        totalTime = totalTime,
        serviceTime = serviceTime,
        validationTime = validationTime,
        globalLockTime = globalLockTime,
        globalLockWaitTime = globalLockWaitTime,
        aborts = aborts?.toString(),
        conflicts = conflicts?.toString(),
        retries = retries?.toString()
    )
}

fun ServiceConflicts.toUiModel(): SysCountersUi.ServiceConflictsUi {
    return SysCountersUi.ServiceConflictsUi(
        serviceType = serviceType.map { it?.toUiModel() }
    )
}

fun ServiceConflicts.ServiceType.toUiModel(): SysCountersUi.ServiceConflictsUi.ServiceTypeUi {
    return SysCountersUi.ServiceConflictsUi.ServiceTypeUi(
        name = name,
        conflicts = conflicts?.toString()
    )
}


fun CdbCounters.toUiModel(): SysCountersUi.CdbUi {
    return SysCountersUi.CdbUi(
        compactions = compactions?.toString(),
        compaction = compaction?.toUiModel(),
        bootTime = bootTime,
        phase0Time = phase0Time,
        phase1Time = phase1Time,
        phase2Time = phase2Time
    )
}

fun CdbCounters.Compaction.toUiModel(): SysCountersUi.CdbUi.CompactionUi {
    return SysCountersUi.CdbUi.CompactionUi(
        ACdb = aCdb?.toString(),
        OCdb = oCdb?.toString(),
        SCdb = sCdb?.toString(),
        total = total?.toString()
    )
}

fun DeviceCounters.toUiModel(): SysCountersUi.DeviceUi {
    return SysCountersUi.DeviceUi(
        connect = connect?.toString(),
        connectFailed = connectFailed?.toString(),
        syncFrom = syncFrom?.toString(),
        syncTo = syncTo?.toString(),
        outOfSync = outOfSync?.toString()
    )
}

fun SessionCounters.toUiModel(): SysCountersUi.SessionUi {
    return SysCountersUi.SessionUi(
        total = total?.toString(),
        netconfTotal = netconfTotal?.toString(),
        restconfTotal = restconfTotal?.toString(),
        jsonrpcTotal = jsonrpcTotal?.toString(),
        snmpTotal = snmpTotal?.toString(),
        cliTotal = cliTotal?.toString()
    )
}
