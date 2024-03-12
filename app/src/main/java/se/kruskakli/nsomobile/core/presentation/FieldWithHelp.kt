package se.kruskakli.nsomobile.core.presentation

data class FieldWithHelp(
    val label: String,
    val value: String,
    val help: String = ""
)