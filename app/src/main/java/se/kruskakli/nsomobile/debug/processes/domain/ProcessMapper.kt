package se.kruskakli.nsomobile.debug.processes.domain

import se.kruskakli.nsomobile.debug.processes.data.NsoProcess

fun NsoProcess.toProcessUi(): ProcessUi {
    return ProcessUi(
        ccall = ccall,
        icall = icall,
        memory = memory,
        msgs = msgs,
        name = name,
        pid = pid,
        reds = reds,
        sharedBinaries = sharedBinaries
    )
}