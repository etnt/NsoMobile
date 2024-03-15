package se.kruskakli.nsomobile.nsopackage.domain

data class PackageUi(
    val name: String,
    val packageVersion: String,
    val description: String,
    val directory: String,
    val ncsMinVersion: List<String>,
    val operStatus: String
)
