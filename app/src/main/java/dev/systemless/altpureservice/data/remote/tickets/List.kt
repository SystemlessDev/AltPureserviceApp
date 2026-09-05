package dev.systemless.altpureservice.data.remote.tickets

data class PureserviceList(
    val name: String,
    val filter: String?,
    val level: Int,
    val iconName: Any?,
    val isDefault: Boolean,
    val disabled: Boolean,
    // Links
    val teamId: Any?,
    val userId: Int?,
    val parentListDefinitionId: Int?,
    val requestTypeId: Int,
    val id: Int,
    val created: String,
    val modified: String,
    val createdById: Int,
    val modifiedById: Int,
    var count: Int?
)
