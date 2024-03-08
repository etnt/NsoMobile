package se.kruskakli.nsomobile.settings.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import se.kruskakli.nsomobile.core.presentation.CustomTextField
import se.kruskakli.nsomobile.core.presentation.InsideCard
import se.kruskakli.nsomobile.settings.domain.SettingsIntent
import se.kruskakli.nsomobile.settings.domain.SettingsState
import se.kruskakli.nsomobile.settings.domain.SettingsViewModel


@Composable
fun SettingsScreen(
) {
    val viewModel = koinViewModel<SettingsViewModel>()
    val newState by viewModel.newState.collectAsState()

    SettingsContent(
        newState,
        { field, value -> viewModel.handleIntent(SettingsIntent.SetFieldValue(field, value)) }
    )
}

@Composable
fun SettingsContent(
    newState: SettingsState,
    onChange: (String, String) -> Unit

) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
            ) {
                /*
                settings.map {
                    InsideCard(
                        header = it.name,
                        fields = it.toFields().filter { field -> field.label != "Name" },
                        extraContent = {
                            IconButton(onClick = { Log.d("SettingScreen", "Remove ${it.name}") }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete")
                            }
                        }
                    )
                }
                */
                Text(
                    text = "Name",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier
                        .padding(start = 20.dp)
                )
                CustomTextField(
                    placeholder = "Name",
                    text = newState.name,
                    onValueChange = { onChange("name", it) },
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    singleLine = true,
                    isError = if (newState.nameError.isNullOrEmpty()) false else true,
                    errorMessage = newState.nameError
                )
                Text(
                    text = "IPv4 Address",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier
                        .padding(start = 20.dp)
                )
                CustomTextField(
                    placeholder = "IP Address",
                    text = newState.ip,
                    onValueChange = { onChange("ip", it) },
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    singleLine = true,
                    isError = if (newState.ipError.isNullOrEmpty()) false else true,
                    errorMessage = newState.ipError
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
        SettingsDataUI(
            name = "Blueberry",
            ip = "192.168.1.147",
            port = "8080",
            user = "admin",
            passwd = "adminPasswd"
        ),
        SettingsDataUI(
            name = "Orange",
            ip = "10.40.142.14",
            port = "8888",
            user = "oper",
            passwd = "operPasswd"
        )
    )
    SettingsContent(
        SettingsState(
            name = "Blueberry",
            ip = "",
            port = "",
            user = "",
            passwd = ""
        ),
        onChange = {field, value -> Log.d("SettingsScreen", "Field: $field, Value: $value")}
    )
}
