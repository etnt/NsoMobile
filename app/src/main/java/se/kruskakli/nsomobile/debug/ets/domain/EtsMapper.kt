package se.kruskakli.nsomobile.debug.ets.domain

import se.kruskakli.nsomobile.debug.ets.data.All

fun All.toEtsUi(): EtsUi {
    return EtsUi(
        id = id,
        mem = mem,
        name = name,
        owner = owner,
        size = size,
        type = type
    )
}
