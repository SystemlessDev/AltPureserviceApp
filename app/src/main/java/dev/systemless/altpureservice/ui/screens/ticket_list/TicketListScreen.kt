package dev.systemless.altpureservice.ui.screens.ticket_list

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.dropUnlessResumed
import dev.systemless.altpureservice.data.remote.tickets.Ticket

@Composable
fun TicketListScreen(
    onClick: (requestId: Int) -> Unit,
    viewModel: TicketListViewModel = hiltViewModel(),
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    if (state.isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn {
            items(state.tickets, key = { it.requestNumber }) {
                TicketColumnItem(it, onClick = onClick)
            }
        }
    }
}

@Composable
fun TicketColumnItem(ticket: Ticket, onClick: (requestId: Int) -> Unit) {
    Card(
        modifier = Modifier.padding(8.dp),
        onClick = dropUnlessResumed() {
            onClick(ticket.requestNumber)
        }
    ) {
        Column {
            Text(ticket.subject)
            Text(ticket.requestNumber.toString())
        }
    }
}
