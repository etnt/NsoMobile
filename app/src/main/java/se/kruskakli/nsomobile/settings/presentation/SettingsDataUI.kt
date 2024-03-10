package se.kruskakli.nsomobile.settings.presentation

import se.kruskakli.nsomobile.core.presentation.Field
import se.kruskakli.nsomobile.settings.data.SettingsData
import se.kruskakli.nsomobile.settings.domain.SystemInfo

// Example usage:
// val settingsData = SettingsData(/* ... */)
// val settingsDataUI = SettingsDataUI(settingsData)
// val fieldMap = settingsDataUI.toFieldMap()

data class SettingsDataUI(
    val name: String = "",
    val ip: String = "",
    val port: String = "",
    val passwd: String = "",
    val user: String = ""
) {
    constructor(settingsData: SettingsData) : this(
        name = settingsData.name,
        ip = settingsData.ip,
        port = settingsData.port,
        user = settingsData.user,
        passwd = settingsData.passwd
    )

    fun toFields(): List<Field> {
        return listOf(
            Field("Name", name),
            Field("IP", ip),
            Field("Port", port),
            Field("User", user),
            Field("Password", passwd)
        )
    }

    fun toSettingsData(): SettingsData {
        return SettingsData(
            name = name,
            ip = ip,
            port = port,
            user = user,
            passwd = passwd
        )
    }

    fun toSystemInfo(): SystemInfo {
        return SystemInfo(
            name = name,
            ip = ip,
            port = port,
            user = user,
            password = passwd
        )
    }
}
