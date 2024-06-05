package se.kruskakli.nsomobile.debug.inet.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import se.kruskakli.nsomobile.Divider
import se.kruskakli.nsomobile.core.presentation.CenteredProgressIndicator
import se.kruskakli.nsomobile.core.presentation.Field
import se.kruskakli.nsomobile.core.presentation.InsideCard
import se.kruskakli.nsomobile.core.presentation.OnFailureMessageBox
import se.kruskakli.nsomobile.debug.inet.domain.InetUi
import se.kruskakli.nsomobile.debug.inet.domain.NsoInetViewModel
import se.kruskakli.nsomobile.debug.inet.domain.NsoInetIntent


@Composable
fun InetScreen(
) {
    val viewModel = koinViewModel<NsoInetViewModel>()
    viewModel.handleIntent(NsoInetIntent.ShowInet)

    DoInetScreen(viewModel)
}

@Composable
fun DoInetScreen(
    viewModel: NsoInetViewModel
) {
    val nsoInet by viewModel.nsoInet.collectAsState()

    nsoInet.DisplayResult(
        onLoading = {
            CenteredProgressIndicator()
        },
        onSuccess = { inet ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(0.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    Divider()
                    InetHeader()
                    LazyColumn {
                        items(items = inet) {
                            Inet(it)
                        }
                    }
                }
            }
        },
        onFailure = {
            OnFailureMessageBox(it)
        }
    )
}

@Composable
fun InetHeader(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Local Address",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 8.dp)
        )
        Text(
            text = "Foreign Address",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodySmall,
        )
        Text(
            text = "State",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(end = 32.dp)
        )
    }
}

@Composable
fun Inet(
    inet: InetUi,
    modifier: Modifier = Modifier
) {
    var show by remember { mutableStateOf(false) }
    val toggleShow = { show = !show }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Divider()
            InetHeadField(inet, toggleShow)
            if (show) {
                val fields = listOf(
                    Field("Foreign Adress", inet.foreignAddress),
                    Field("Local Adress", inet.localAddress),
                    Field("Module", inet.module),
                    Field("Owner", inet.owner),
                    Field("Port", inet.port),
                    Field("Received", inet.received),
                    Field("Sent", inet.sent),
                    Field("State", inet.state),
                    Field("Type", inet.type ?: "-")
                )
                InsideCard(
                    header = "Network Listeners",
                    fields = fields,
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp)
                )
            }
            Divider()
        }
    }
}

@Composable
fun InetHeadField(
    inet: InetUi,
    toggleShow: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { toggleShow() })
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = inet.localAddress,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 4.dp)
        )
        Text(
            text = inet.foreignAddress,
            style = MaterialTheme.typography.bodySmall,
        )
        Text(
            text = inet.state,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(end = 4.dp)
        )
    }
}

