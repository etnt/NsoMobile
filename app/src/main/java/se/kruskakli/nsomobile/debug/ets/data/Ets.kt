package se.kruskakli.nsomobile.debug.ets.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Ets(
    @SerialName("nso-dbg:ets-tables") val nsoEtsTables: NsoEtsTables
)

