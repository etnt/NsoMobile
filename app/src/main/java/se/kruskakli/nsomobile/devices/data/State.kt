package se.kruskakli.nsomobile.devices.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


// Show states for the device
@Serializable
data class State(
    @SerialName("oper-state") val operState: String,               // The actual operational state of the device
    @SerialName("transaction-mode") val transactionMode: String?,   // Indicate datastore capabilities
    @SerialName("admin-state") val adminState: String              // Controls configuration and southbound communication
)