package org.agilemetrics.core.agilemetrics.presentation.controller.addworkitem

import org.agilemetrics.core.agilemetrics.business.domain.WorkItem
import org.agilemetrics.core.agilemetrics.business.domain.WorkItemTransition
import java.time.LocalDateTime

data class AddWorkItemIn(val name: String,
                         val transitions: List<AddWorkItemTransition>) {
}

data class AddWorkItemOut(val id: String,
                          val name: String,
                          val created: LocalDateTime,
                          val transitions: List<AddWorkItemTransition>) {

    companion object {
        fun from(domain: WorkItem) = AddWorkItemOut(
                id = domain.id!!,
                name = domain.name,
                created = domain.created!!,
                transitions = domain.transitions.map {
                    AddWorkItemTransition.from(it)
                }
        )
    }

}

data class AddWorkItemTransition(val column: String,
                                 val date: LocalDateTime) {

    companion object {
        fun from(domain: WorkItemTransition) = AddWorkItemTransition(
                column = domain.column,
                date = domain.date
        )
    }

}
