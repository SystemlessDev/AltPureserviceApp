package dev.systemless.altpureservice.data.remote.tickets

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TicketApi {
    @GET("/agent/api/ticket/{ticket_id}")
    suspend fun getTicket(
        @Path("ticket_id") ticketId: Int
    ): List<Ticket>

    @GET("/agent/api/ticket/")
    suspend fun getTicketList(
        @Query("limit") limit: Int = 50,
        @Query("start") start: Int = 0,
        @Query("filter") queryFilter: String? = "AssignedAgentId == [USERID]" // TODO: make query generator
    ): List<Ticket>

    // UserId == NULL || UserId == 20306
    @GET("/agent/api/listdefinition")
    suspend fun getLists(
        @Query("filter") queryFilter: String = "UserId == NULL || UserId == [USERID]"
    ): List<PureserviceList>

    @GET("/agent/api/ticket/count")
    suspend fun getCount(
        @Query("filter") queryFilter: String? = null
    ): Count
}
