package se.kruskakli.nsomobile.nsopackage.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class NsoPackage(
    val name: String,
    @SerialName("package-version") val packageVersion: String,
    val description: String,
    val directory: String,
    val component: List<Component?> = emptyList(),
    @SerialName("ncs-min-version") val ncsMinVersion: List<String>,
    @SerialName("oper-status") val operStatus: JsonObject

    /*
    @Transient val component: List<Component>? = null,
    @Transient val operStatus: OperStatus? = null,
    @Transient val templateLoadingMode: String? = null,
    @Transient val templates: List<String>? = null

    @Expose(deserialize = false) // deserialize is this filed is not required
    @SerializedName("component") val component: List<Component>,

    @Expose(deserialize = false) // deserialize is this filed is not required
    @SerializedName("oper-status") val operStatus: OperStatus,

    @Expose(deserialize = false) // deserialize is this filed is not required
    @SerializedName("template-loading-mode") val templateLoadingMode: String,

    @Expose(deserialize = false) // deserialize is this filed is not required
    @SerializedName("templates") val templates: List<String>
    */
)