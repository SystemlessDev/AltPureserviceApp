package dev.systemless.altpureservice.util

data class PureserviceFilter(
    val assignmentType: FilterTypes,
    val assignmentOperator: FilterOperator,
    val assigned: Any,
)

enum class FilterTypes(
    val v: String
) {
    ASSIGNED_DEPARTMENT("AssignedDepartmentId"), ASSIGNED_TEAM("")
}

enum class FilterOperator(
    val v: String
) {
    EQUALS("=="), NOT_EQUALS("!=")
}

enum class AssignmentSpecialities(
    val v: String
) {
    MAGIC_ASSIGNED_CURRENT_TEAM("@0.Contains(outerIt.AssignedTeamId.Value)"), MAGIC_ASSIGNED_CURRENT_AGENT(
        "[USERID]"
    )
}

/*
    Start with an open bracket on each query. Check what the next value in the list is.
    If it is the same as the current one: add a OR statement ( || )
    If it is not the same: add the string ") && ("
 */
fun PureserviceFilterToString(filter: List<PureserviceFilter>): String {
    var queryString: String = ""

    var lastItem: FilterTypes? = null
    filter.sortedBy { it.assignmentType }.forEach { it ->
        // Assignments to current team is set to @0.Contains(outerIt.AssignedTeamId.Value)
        if (it.assigned == AssignmentSpecialities.MAGIC_ASSIGNED_CURRENT_TEAM) {
            queryString =
                AssignmentSpecialities.MAGIC_ASSIGNED_CURRENT_TEAM.v + " && " + queryString
            return@forEach
        }

        queryString += when (lastItem) {
            null -> {
                "("
            }

            it.assignmentType -> {
                " || "
            }

            else -> {
                ") && ("
            }
        }

        var assigned: Any = it.assigned

        // Current agent is the string value [USERID]
        if (it.assigned == AssignmentSpecialities.MAGIC_ASSIGNED_CURRENT_AGENT) {
            assigned = AssignmentSpecialities.MAGIC_ASSIGNED_CURRENT_AGENT.v
        }

        // Categories are set to [catid1, catid2, catid3]
        // If you take an entire category 1, you only have to set [123]
        // If you want to specify one, you need to set [123,456,789]
        if (it.assigned is Triple<*, *, *>) {
            var (cat1, cat2, cat3) = it.assigned
            if (cat1 == null) {
                cat1 = "NULL"
            } else if (cat2 == null) {
                cat2 = "NULL"
            } else if (cat3 == null) {
                cat3 = "NULL"
            }

            assigned = "[$cat1, $cat2, $cat3]"
        }

        queryString += it.assignmentType.v + it.assignmentOperator.v + assigned

        lastItem = it.assignmentType
    }
    queryString += ")"

    return queryString
}
