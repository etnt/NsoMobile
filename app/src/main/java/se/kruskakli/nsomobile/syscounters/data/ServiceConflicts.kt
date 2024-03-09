package se.kruskakli.nsomobile.syscounters.data

import kotlinx.serialization.Serializable


@Serializable
data class ServiceConflicts(
    val serviceType: List<ServiceType?> = emptyList()
) {
    @Serializable
    data class ServiceType(
        val name: String? = null,
        val conflicts: Long? = null
    ) 
}
