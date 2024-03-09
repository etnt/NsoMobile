package se.kruskakli.nsomobile.syscounters.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Transaction(
    val datastore: List<Datastore?> = emptyList()
) {
    @Serializable
    data class Datastore(
        val name: Name? = null,
        val commit: Long? = null,
        @SerialName("total-time") val totalTime: String? = null,
        @SerialName("service-time") val serviceTime: String? = null,
        @SerialName("validation-time") val validationTime: String? = null,
        @SerialName("global-lock-time") val globalLockTime: String? = null,
        @SerialName("global-lock-wait-time") val globalLockWaitTime: String? = null,
        val aborts: Long? = null,
        val conflicts: Long? = null,
        val retries: Long? = null,
    ) {
        @Serializable
        enum class Name {
            operational,
            running
        }
    }
}