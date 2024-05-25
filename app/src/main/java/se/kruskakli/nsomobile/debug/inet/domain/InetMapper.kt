package se.kruskakli.nsomobile.debug.inet.domain

import se.kruskakli.nsomobile.debug.inet.data.All

fun All.toInetUi(): InetUi {
    return InetUi(
        foreignAddress = foreignAddress,
        localAddress = localAddress,
        module = module,
        owner = owner,
        port = port,
        received = received,
        sent = sent,
        state = state,
        type = type
    )
}