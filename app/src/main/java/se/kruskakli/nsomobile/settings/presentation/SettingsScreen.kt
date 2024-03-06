package se.kruskakli.nsomobile.settings.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.koinViewModel
import se.kruskakli.nsomobile.core.presentation.Field
import se.kruskakli.nsomobile.core.presentation.InsideCard
import se.kruskakli.nsomobile.releasenote.domain.ReleaseNoteViewModel
import se.kruskakli.nsomobile.releasenote.presentation.ReleaseNoteContent
import se.kruskakli.nsomobile.settings.data.SettingsData
import se.kruskakli.nsomobile.settings.domain.SettingsViewModel


@Composable
fun SettingsScreen(
) {
    val viewModel = koinViewModel<SettingsViewModel>()
    val settings by viewModel.settings.collectAsState()
    SettingsContent(settings)
}

@Composable
fun SettingsContent(
    settings: List<SettingsData>
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn {
            items(settings) { setting ->
                val fields = listOf(
                    Field(label = "IP", value = setting.ip),
                    Field(label = "Port", value = setting.port),
                    Field(label = "Password", value = setting.passwd),
                    Field(label = "User", value = setting.user)
                )
                InsideCard(
                    header = setting.name,
                    fields = fields
                )
            }
        }
    }
}


@Preview
@Composable
fun SettingsScreenPreview(
) {
    val settings = listOf(
        SettingsData(
            name = "Blueberry",
            ip = "192.168.1.147",
            port = "8080",
            user = "admin",
            passwd = "adminPasswd"
        ),
        SettingsData(
            name = "Orange",
            ip = "10.40.142.14",
            port = "8888",
            user = "oper",
            passwd = "operPasswd"
        )
    )
    SettingsContent(settings)
}
