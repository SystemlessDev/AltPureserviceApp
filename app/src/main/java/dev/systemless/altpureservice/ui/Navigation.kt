package dev.systemless.altpureservice.ui

import dev.systemless.altpureservice.R
import dev.systemless.altpureservice.data.remote.tickets.Ticket

sealed interface TopLevelRoute {
    val icon: Int
}

val TOP_LEVEL_ROUTES : List<TopLevelRoute> = listOf(FilterList, Search, Settings)

data object FilterList : TopLevelRoute { override val icon = R.drawable.ic_favorite}

data class TicketList(val name: String, val filter: String?)

data object Search : TopLevelRoute { override val icon = R.drawable.ic_home}

data object Settings : TopLevelRoute { override val icon = R.drawable.ic_account_box}

data class TicketDetail(val ticketId: Int)

data object Login
