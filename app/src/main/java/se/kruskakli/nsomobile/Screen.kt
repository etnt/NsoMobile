package se.kruskakli.nsomobile

sealed class Screen(val route: String) {
    object SettingsScreen : Screen("settings_screen")
    object ReleaseNoteScreen : Screen("release_notes_screen")
    object SysCountersScreen : Screen("syscounters_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach {
                append("/$it")
            }
        }
    }

}
