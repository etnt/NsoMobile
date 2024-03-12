package se.kruskakli.nsomobile.main.presentation


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import se.kruskakli.nsomobile.Divider
import se.kruskakli.nsomobile.R
import se.kruskakli.nsomobile.releasenote.presentation.ReleaseNoteScreen
import se.kruskakli.nsomobile.settings.presentation.SettingsScreen
import se.kruskakli.nsomobile.syscounters.presentation.SysCountersScreen


enum class TabPage {
    //Home,
    Settings,
    //Packages, Devices, Error, Alarms, About,
    //Processes, Listeners, EtsTables, Allocators,
    SysCounters,
    ReleaseNotes
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(

) {
    var drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var selectedItemIndex by rememberSaveable { mutableStateOf(0) }
    val menuItems = MenuItems()

    val nsoDbgEnabled = false    // FIXME

    var page by remember { mutableStateOf(TabPage.Settings) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet{
                Spacer(modifier = Modifier.height(16.dp))
                menuItems.forEachIndexed { index, item ->
                    if (item.hasSubItems) {
                        if (nsoDbgEnabled) {
                            CustomNestedMenu(
                                item,
                                fun(newPage: TabPage) {
                                    page = newPage
                                    scope.launch {
                                        drawerState.close()
                                    }
                                }
                            )
                        }
                    } else {
                        NavigationDrawerItem(
                            label = {
                                Text(
                                    text = item.title,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = if (index == selectedItemIndex) {
                                        FontWeight.Bold
                                    } else {
                                        FontWeight.Normal
                                    }
                                )
                            },
                            selected = index == selectedItemIndex,
                            onClick = {
                                selectedItemIndex = index
                                page = item.page
                                scope.launch{
                                    drawerState.close()
                                }
                            },
                            icon = {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    imageVector = if (index == selectedItemIndex) {
                                        item.selectedIcon
                                    } else {
                                        item.unSelectedIcon
                                    },
                                    contentDescription = item.title
                                )
                            },
                            modifier = Modifier
                                .padding(NavigationDrawerItemDefaults.ItemPadding)
                        )
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("NSO Mobile")
                            IconButton(onClick = {
                                // FIXME: viewModel.handleIntent(MainIntent.RefreshPage(page))
                            }) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_flower),
                                    contentDescription = "Refresh")
                            }
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(
                                Icons.Filled.Menu,
                                contentDescription = "Menu"
                            )
                        }
                    }
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .padding(
                        top = padding.calculateTopPadding(),
                        start = padding.calculateLeftPadding(LayoutDirection.Ltr),
                        end = padding.calculateEndPadding(LayoutDirection.Ltr)
                    )
            ) {
                Divider()
                /*
                if (apiError != null) {
                    page = TabPage.Error
                }
                 */
                when (page) {
                    /*
                    TabPage.Home -> {
                        WelcomePage()
                    }
                     */

                    TabPage.Settings -> {
                        SettingsScreen()
                    }

                    TabPage.ReleaseNotes -> {
                        ReleaseNoteScreen()
                    }

                    TabPage.SysCounters -> {
                        /*
                        if (loading) {
                            LoadingState()
                        } else {
                         */
                            SysCountersScreen()
                        //}
                    }

                    /*
                    TabPage.Packages -> {
                        viewModel.handleIntent(MainIntent.ShowPackages)
                        if (loading) {
                            LoadingState()
                        } else {
                            PackagesScreen(nsoPackages)
                        }
                    }

                    TabPage.Devices -> {
                        viewModel.handleIntent(MainIntent.ShowDevices)
                        if (loading) {
                            LoadingState()
                        } else {
                            DevicesScreen(nsoDevices)
                        }
                    }

                    TabPage.Alarms -> {
                        viewModel.handleIntent(MainIntent.ShowAlarms)
                        if (loading) {
                            LoadingState()
                        } else {
                            AlarmsScreen(nsoAlarms)
                        }
                    }

                    TabPage.About -> {
                        AboutPage(releaseNotes)
                    }

                    TabPage.Listeners -> {
                        viewModel.handleIntent(MainIntent.ShowInet)
                        if (loading) {
                            LoadingState()
                        } else {
                            InetScreen(nsoInet)
                        }
                    }

                    TabPage.EtsTables -> {
                        viewModel.handleIntent(MainIntent.ShowEts)
                        if (loading) {
                            LoadingState()
                        } else {
                            EtsScreen(nsoEts, viewModel)
                        }
                    }

                    TabPage.Allocators -> {
                        viewModel.handleIntent(MainIntent.ShowAllocators)
                        if (loading) {
                            LoadingState()
                        } else {
                            AllocatorScreen(nsoAllocators)
                        }
                    }

                    TabPage.Processes -> {
                        viewModel.handleIntent(MainIntent.ShowProcesses)
                        if (loading) {
                            LoadingState()
                        } else {
                            ProcessScreen(nsoProcesses, viewModel)
                        }
                    }

                    TabPage.Error -> {
                        ErrorPage(apiError, viewModel)
                    }
                     */
                }
            }
        }
    }
}

/*
 * Since the ModalDrawerSheet composable didn't support nested menus,
 * I had to create a custom composable for the nested menu items.
 */
@Composable
fun CustomNestedMenu(
    item: NavigationItem,
    onClick: (TabPage) -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .padding(start = 30.dp, bottom = 5.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = item.selectedIcon,
                contentDescription = item.title,
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = item.title,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .padding(start = 10.dp)
            )
        }
        item.subItems.forEach { subItem ->
            Row(
                modifier = Modifier
                    .padding(start = 55.dp, bottom = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = "SubItem",
                    modifier = Modifier.size(10.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
                val text = AnnotatedString.Builder().apply {
                    withStyle(
                        style = SpanStyle(
                            fontSize = MaterialTheme.typography.labelSmall.fontSize,
                            fontStyle = FontStyle.Italic,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        append(subItem.title)
                    }
                }.toAnnotatedString()
                ClickableText(
                    text = text,
                    onClick = { onClick(subItem.page) },
                    modifier = Modifier
                        .padding(start = 10.dp)
                )
            }
        }
    }
}

data class NavigationItem(
    val title: String,
    val page: TabPage = TabPage.Settings,  // FIXME: shoud be: Home
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val subItems: List<NavigationItem> = emptyList()
) {
    val hasSubItems: Boolean
        get() = subItems.isNotEmpty()
}

@Composable
private fun MenuItems(): List<NavigationItem> {
    return listOf(
        /*
        NavigationItem(
            title = "Home",
            page = TabPage.Home,
            selectedIcon = Icons.Filled.Home,
            unSelectedIcon = Icons.Outlined.Home
        ),
         */
        NavigationItem(
            title = "Settings",
            page = TabPage.Settings,
            selectedIcon = Icons.Filled.Settings,
            unSelectedIcon = Icons.Outlined.Settings
        ),
        /*
        NavigationItem(
            title = "Alarms",
            page = TabPage.Alarms,
            selectedIcon = RememberAlarms(),
            unSelectedIcon = RememberAlarms()
        ),
        NavigationItem(
            title = "Devices",
            page = TabPage.Devices,
            selectedIcon = RememberDevices(),
            unSelectedIcon = RememberDevices()
        ),
        NavigationItem(
            title = "Packages",
            page = TabPage.Packages,
            selectedIcon = RememberPackages(),
            unSelectedIcon = RememberPackages()
        ),
         */
        NavigationItem(
            title = "System Counters",
            page = TabPage.SysCounters,
            selectedIcon = ImageVector.vectorResource(id = R.drawable.ic_counters),
            unSelectedIcon = ImageVector.vectorResource(id = R.drawable.ic_counters),
        ),
        /*
        NavigationItem(
            title = "About",
            page = TabPage.About,
            selectedIcon = RememberQuestionMark(),
            unSelectedIcon = RememberQuestionMark()
        ),
        NavigationItem(
            title = "Debug",
            selectedIcon = ImageVector.vectorResource(id = R.drawable.ic_bug),
            unSelectedIcon = Icons.Outlined.Build,
            subItems = listOf(
                NavigationItem(
                    title = "Processes",
                    page = TabPage.Processes,
                    selectedIcon = RememberQuestionMark(),
                    unSelectedIcon = RememberQuestionMark()
                ),
                NavigationItem(
                    title = "Network Listeners",
                    page = TabPage.Listeners,
                    selectedIcon = RememberQuestionMark(),
                    unSelectedIcon = RememberQuestionMark()
                ),
                NavigationItem(
                    title = "ETS tables",
                    page = TabPage.EtsTables,
                    selectedIcon = RememberQuestionMark(),
                    unSelectedIcon = RememberQuestionMark()
                ),
                NavigationItem(
                    title = "ERTS Allocators",
                    page = TabPage.Allocators,
                    selectedIcon = RememberQuestionMark(),
                    unSelectedIcon = RememberQuestionMark()
                )
            )
        )

         */
    )
}
