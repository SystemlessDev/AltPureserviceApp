package dev.systemless.altpureservice.ui.screens.filter_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.systemless.altpureservice.data.remote.tickets.PureserviceList
import dev.systemless.altpureservice.ui.TicketList
import dev.systemless.altpureservice.ui.screens.ticket_list.TicketListScreen


@Composable
fun FilterListScreen(
    onClick: (TicketList) -> Unit,
    viewModel: FilterListViewmodel = hiltViewModel(),
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
            items(state.lists, key = { it.id }) {
                FilterItemComponent(it, onClick = {
                    onClick(TicketList(it.name, it.filter))
                })
                HorizontalDivider(thickness = 1.dp)
            }
        }
    }
}

@Composable
fun FilterItemComponent(filter: PureserviceList, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        var filterCount = filter.count.toString()
        if (filter.count == null) {
            filterCount = "N/A"
        }
        Text(
            filterCount, textAlign = TextAlign.Center, modifier = Modifier
                .drawBehind(
                    onDraw = {
                        drawRoundRect(Color(0xFFBBAAEE), cornerRadius = CornerRadius(10.dp.toPx()))
                    })
                .padding(4.dp)
                .padding()

        )
        Text(filter.name, modifier = Modifier.padding(horizontal = 16.dp))
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewFilterItemComponent(
    filter: PureserviceList = PureserviceList(
        name = "Test list",
        filter = "",
        level = 0,
        iconName = null,
        isDefault = true,
        disabled = false,
        teamId = 0,
        userId = 0,
        parentListDefinitionId = 0,
        requestTypeId = 0,
        id = 0,
        created = "",
        modified = "",
        createdById = 0,
        modifiedById = 0,
        count = 99
    )
) {
    FilterItemComponent(filter, {})
}
