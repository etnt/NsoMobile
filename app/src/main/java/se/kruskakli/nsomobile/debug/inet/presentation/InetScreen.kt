package se.kruskakli.nsomobile.debug.inet.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel
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
}