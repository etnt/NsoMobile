package se.kruskakli.nsomobile

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.koin.androidx.compose.koinViewModel
import se.kruskakli.nsomobile.releasenote.di.releaseNoteModule
import se.kruskakli.nsomobile.releasenote.presentation.ReleaseNoteScreen
import se.kruskakli.nsomobile.settings.domain.SettingsViewModel
import se.kruskakli.nsomobile.settings.presentation.SettingsScreen
import se.kruskakli.nsomobile.syscounters.presentation.SysCountersScreen
import se.kruskakli.nsomobile.ui.theme.NsoMobileTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NsoMobileTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Log.d("MainActivity", "Hello World!")
                    val viewModel = koinViewModel<SettingsViewModel>()
                    //ReleaseNoteScreen()
                    //SettingsScreen()
                    SysCountersScreen()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NsoMobileTheme {
        Greeting("Android")
    }
}