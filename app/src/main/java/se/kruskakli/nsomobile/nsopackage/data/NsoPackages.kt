package se.kruskakli.nsomobile.nsopackage.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NsoPackages(
    @SerialName("tailf-ncs:packages") val tailfNcsPackages: TailfNcsPackages
)