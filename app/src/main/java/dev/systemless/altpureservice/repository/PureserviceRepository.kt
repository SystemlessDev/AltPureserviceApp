package dev.systemless.altpureservice.repository

import android.util.Log
import dev.systemless.altpureservice.data.remote.tickets.PureserviceList
import dev.systemless.altpureservice.data.remote.tickets.Ticket
import dev.systemless.altpureservice.data.remote.tickets.TicketApi
import dev.systemless.altpureservice.util.Resource
import javax.inject.Inject

class PureserviceRepository @Inject constructor(
    private val ticketApi: TicketApi
) {
    suspend fun getPureserviceTicket(ticketId: Int): Resource<Ticket> {
        val response = try {
            ticketApi.getTicket(ticketId)[0]
        } catch (e: Exception) {
            return Resource.Error(null, "Unknown error")
        }
        return Resource.Success(response)
    }

    suspend fun getPureserviceTicketList(queryFilter: String?): List<Ticket> = ticketApi.getTicketList(queryFilter = queryFilter)

    suspend fun getPureserviceLists(): List<PureserviceList> {
        val list = ticketApi.getLists().filter { it.requestTypeId == 1 } // Magic number that means tickets ( :( )
        Log.i("count", list.toString())
        list.forEachIndexed { index, value ->
            try {
                val count = ticketApi.getCount(value.filter)
                list[index].count = count.count
            } catch (_: Exception) {
                Log.e("count", "Failed to fetch count for " + value.name + " filter query: " + value.filter.toString())
                list[index].count = null
            }
        }
        return list
    }
}
