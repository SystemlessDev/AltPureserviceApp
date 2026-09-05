package dev.systemless.altpureservice.data.remote.tickets

data class Ticket(
    val id: Int, // ID is the internal number pureservice uses, meanwhile requestNumber is the "public" INC id.
    val solution: String?,
    val emailAddress: String,
    val userWaitedSince: String?,
    val requestNumber: Int,
    val subject: String,
    val description: String,
)
