package se.kruskakli.nsomobile.syscounters.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class SessionCounters(
    val total: Long? = null,
    @SerialName("netconf-total") val netconfTotal: Long? = null,
    @SerialName("restconf-total") val restconfTotal: Long? = null,
    @SerialName("jsonrpc-total") val jsonrpcTotal: Long? = null,
    @SerialName("snmp-total") val snmpTotal: Long? = null,
    @SerialName("cli-total") val cliTotal: Long? = null
)
