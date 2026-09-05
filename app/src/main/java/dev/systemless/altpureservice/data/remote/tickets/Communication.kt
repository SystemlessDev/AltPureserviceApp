package dev.systemless.altpureservice.data.remote.tickets

data class Communication(
    val created: String,
    val createdById: Int,
    val direction: Int,
    val id: Int,
    val isBoundary: Boolean,
    val isPinned: Boolean,
    val messageId: Int,
    val modified: String,
    val modifiedById: Int,
    //val parentCommunicationId: Any,
    //val relatedRequestNumbers: Any,
    val senderId: Int,
    val status: Int,
    val statusMessage: Any,
    val subject: String,
    val text: String,
    val ticketId: Int,
    val type: Int,
    val typeName: String,
    val visibility: Int
)
